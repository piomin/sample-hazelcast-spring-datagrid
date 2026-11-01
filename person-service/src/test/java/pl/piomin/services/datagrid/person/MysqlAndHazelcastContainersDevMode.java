package pl.piomin.services.datagrid.person;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.mysql.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class MysqlAndHazelcastContainersDevMode {

    @Bean
    @ServiceConnection
    public MySQLContainer mysql() {
        return new MySQLContainer("mysql:9.7")
                .withConfigurationOverride("mysql-conf")
                .withUsername("datagrid")
                .withPassword("datagrid");
    }

    @Bean
    public GenericContainer<?> hazelcast(DynamicPropertyRegistry registry) {
        GenericContainer<?> hazelcast = new GenericContainer<>(DockerImageName.parse("hazelcast/hazelcast:5.7"))
                .withExposedPorts(5701);
        registry.add("spring.hazelcast.url", () -> hazelcast.getHost() + ":" + hazelcast.getFirstMappedPort());
        return hazelcast;
    }

}
