package pl.piomin.services.datagrid.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.hazelcast.config.Config;
import com.hazelcast.config.ManagementCenterConfig;

@SpringBootApplication
public class EmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}

	@Bean
	Config config() {
		Config c = new Config();
		c.setInstanceName("cache-1");
		c.getGroupConfig().setName("dev").setPassword("dev-pass");
		ManagementCenterConfig mcc = new ManagementCenterConfig().setUrl("http://192.168.99.100:38080/mancenter").setEnabled(true);
		c.setManagementCenterConfig(mcc);
		return c;
	}
	
}
