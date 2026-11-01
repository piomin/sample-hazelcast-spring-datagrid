package pl.piomin.services.employee;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.List;

import com.github.dockerjava.api.DockerClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.piomin.services.employee.model.Employee;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.Container.ExecResult;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.k3s.K3sContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

/**
 * Runs the application as a Hazelcast cluster inside a K3s container and checks
 * that the members discover each other through the Kubernetes API and share data.
 */
@Testcontainers
class EmployeeKubernetesIT {

    private static final int NODE_PORT = 30080;
    private static final int REPLICAS = 2;
    private static final String IMAGE = "employee-service:it";

    @Container
    static final K3sContainer K3S = new K3sContainer(DockerImageName.parse("rancher/k3s:v1.33.5-k3s1"))
            .withExposedPorts(6443, 8443, NODE_PORT);

    static RestClient client;

    @BeforeAll
    static void deploy() throws Exception {
        buildApplicationImage();
        loadImageIntoK3s();
        K3S.copyFileToContainer(MountableFile.forClasspathResource("employee-k8s.yaml"), "/tmp/employee-k8s.yaml");
        kubectl("apply", "-f", "/tmp/employee-k8s.yaml");
        kubectl("rollout", "status", "deployment/employee-service", "--timeout=120s");
        client = RestClient.create("http://" + K3S.getHost() + ":" + K3S.getMappedPort(NODE_PORT));
    }

    @AfterAll
    static void removeImage() {
        try {
            DockerClientFactory.lazyClient().removeImageCmd(IMAGE).withForce(true).exec();
        } catch (RuntimeException e) {
            // the image is only a build artifact of this test, failing to remove it must not fail the build
        }
    }

    @Test
    void hazelcastMembersFormClusterThroughKubernetes() throws Exception {
        try {
            await().atMost(Duration.ofMinutes(2)).pollInterval(Duration.ofSeconds(3)).untilAsserted(() -> {
                String logs = kubectl("logs", "-l", "app=employee-service", "--tail=-1", "--prefix");
                assertThat(logs).contains("Members {size:" + REPLICAS);
            });
        } catch (AssertionError e) {
            throw new AssertionError(e.getMessage() + "\n" + diagnostics(), e);
        }
    }

    @Test
    void dataIsSharedBetweenPods() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setPersonId(10);
        employee.setCompany("Test");
        employee.setPosition("Developer");
        employee.setSalary(10000);
        client.post().uri("/employees").body(employee).retrieve().toBodilessEntity();

        await().atMost(Duration.ofMinutes(1)).untilAsserted(() -> {
            // every call goes through the NodePort service, so it may land on any pod
            for (int i = 0; i < 10; i++) {
                Employee found = client.get().uri("/employees/{id}", 1).retrieve().body(Employee.class);
                assertThat(found).isNotNull();
                assertThat(found.getCompany()).isEqualTo("Test");
            }
            List<Employee> byCompany = client.get().uri("/employees/company/{c}", "Test")
                    .retrieve().body(new ParameterizedTypeReference<>() {});
            assertThat(byCompany).hasSize(1);
        });
    }

    private static void buildApplicationImage() {
        Path jar = Path.of(System.getProperty("app.jar", "target/employee-kubernetes-service-1.0-SNAPSHOT.jar"));
        assertThat(jar).as("run the test with mvn verify so the application jar is built first").exists();
        new ImageFromDockerfile(IMAGE, false)
                .withFileFromPath("app.jar", jar)
                .withDockerfileFromBuilder(b -> b.from("eclipse-temurin:21-jre")
                        .copy("app.jar", "/app.jar")
                        .entryPoint("java", "-jar", "/app.jar")
                        .build())
                .get();
    }

    private static void loadImageIntoK3s() throws IOException, InterruptedException {
        DockerClient docker = DockerClientFactory.lazyClient();
        Path tar = Files.createTempFile("employee-service", ".tar");
        try (InputStream in = docker.saveImageCmd("employee-service").withTag("it").exec()) {
            Files.copy(in, tar, StandardCopyOption.REPLACE_EXISTING);
            K3S.copyFileToContainer(MountableFile.forHostPath(tar), "/tmp/employee-service.tar");
        } finally {
            Files.deleteIfExists(tar);
        }
        ExecResult result = K3S.execInContainer("ctr", "-n", "k8s.io", "images", "import", "--all-platforms", "/tmp/employee-service.tar");
        assertThat(result.getExitCode()).as(result.getStderr()).isZero();
    }

    private static String diagnostics() throws IOException, InterruptedException {
        return K3S.execInContainer("kubectl", "get", "pods", "-o", "wide").getStdout()
                + K3S.execInContainer("kubectl", "describe", "pods").getStdout()
                + K3S.execInContainer("kubectl", "logs", "-l", "app=employee-service", "--tail=200", "--prefix", "--previous").getStdout();
    }

    private static String kubectl(String... args) throws IOException, InterruptedException {
        String[] cmd = new String[args.length + 1];
        cmd[0] = "kubectl";
        System.arraycopy(args, 0, cmd, 1, args.length);
        ExecResult result = K3S.execInContainer(cmd);
        assertThat(result.getExitCode()).as(result.getStderr()).isZero();
        return result.getStdout();
    }

}
