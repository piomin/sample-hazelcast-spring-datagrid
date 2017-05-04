package pl.piomin.services.datagrid.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.hazelcast.config.Config;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import pl.piomin.services.datagrid.employee.data.EmployeeSerializer;
import pl.piomin.services.datagrid.employee.model.Employee;

@SpringBootApplication
public class MemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberApplication.class, args);
	}

	@Bean
	HazelcastInstance hazelcastInstance() {
		Config config = new Config();
		SerializerConfig sc = new SerializerConfig()
				.setTypeClass(Employee.class).setClass(EmployeeSerializer.class);
//				.setClassName("com.hazelcast.hibernate.serialization.Hibernate5CacheEntrySerializer")
//			    .setImplementation(new Hibernate5CacheEntrySerializerHook().createSerializer())
//			    .setTypeClassName("pl.piomin.services.datagrid.person.model.Person");
		config.getSerializationConfig().addSerializerConfig(sc);
		HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
		return instance;
	}
	
}
