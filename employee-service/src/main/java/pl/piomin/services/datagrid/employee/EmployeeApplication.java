package pl.piomin.services.datagrid.employee;

import com.hazelcast.client.ClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.config.Config;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.core.HazelcastInstance;

import pl.piomin.services.datagrid.employee.data.EmployeeSerializer;
import pl.piomin.services.datagrid.employee.model.Employee;

@SpringBootApplication
public class EmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}

//	@Bean
	HazelcastInstance hazelcastInstance() {
		ClientConfig config = new ClientConfig();
		config.getGroupConfig().setName("dev").setPassword("dev-pass");
//		config.getNetworkConfig().addAddress("192.168.99.100:5701");
		// TODO - fixme
//		config.getNetworkConfig().addAddress("localhost:5701");
		
//		SerializerConfig sc = new SerializerConfig()
//				.setTypeClass(Employee.class).setClass(EmployeeSerializer.class);
//		config.getSerializationConfig().addSerializerConfig(sc);
		
		HazelcastInstance instance = HazelcastClient.newHazelcastClient(config);
		return instance;
	}
	
	@Bean
	Config config() {
		Config c = new Config();
		c.setInstanceName("cache-1");
		c.getGroupConfig().setName("dev").setPassword("dev-pass");
		ManagementCenterConfig mcc = new ManagementCenterConfig().setUrl("http://192.168.99.100:38080/mancenter").setEnabled(true);
		c.setManagementCenterConfig(mcc);
		// TODO - fixme
//		SerializerConfig sc = new SerializerConfig().setTypeClass(Employee.class).setClass(EmployeeSerializer.class);
//		c.getSerializationConfig().addSerializerConfig(sc);
		return c;
	}
	
}
