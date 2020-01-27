package pl.piomin.services.employee;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.HazelcastInstance;
import pl.piomin.services.employee.model.Employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.hazelcast.repository.config.EnableHazelcastRepositories;

@SpringBootApplication
@EnableHazelcastRepositories
public class EmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}

//	@Bean
//	HazelcastInstance hazelcastInstance() {
//		Config config = new Config();
//		config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
//		config.getNetworkConfig().getJoin().getKubernetesConfig().setEnabled(true)
//				.setProperty("namespace", "MY-KUBERNETES-NAMESPACE")
//				.setProperty("service-name", "MY-SERVICE-NAME");
//		config.getGroupConfig().setName("dev").setPassword("dev-pass");
//		config.getNetworkConfig().addAddress("192.168.99.100:5701");
//		config.getNetworkConfig().addAddress("localhost:5701");
		
//		SerializerConfig sc = new SerializerConfig()
//				.setTypeClass(Employee.class);
//				.setClassName("com.hazelcast.hibernate.serialization.Hibernate5CacheEntrySerializer")
//			    .setImplementation(new Hibernate5CacheEntrySerializerHook().createSerializer())
//			    .setTypeClassName("pl.piomin.services.datagrid.person.model.Person");
//		config.getSerializationConfig().addSerializerConfig(sc);
		
//		HazelcastInstance instance = HazelcastClient.newHazelcastClient(config);
//		return null;
//	}
	
	@Bean
	Config config() {
		Config config = new Config();
		config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(false);// TODO - remove if not required
		config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
		config.getNetworkConfig().getJoin().getKubernetesConfig().setEnabled(true)
				.setProperty("namespace", "default")
				.setProperty("service-name", "hazelcast-service");
		return config;
	}

}
