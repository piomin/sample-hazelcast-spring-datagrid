package pl.piomin.services.datagrid.person;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.HazelcastInstance;

@SpringBootApplication
public class PersonApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonApplication.class, args);
	}

	@Bean
	HazelcastInstance hazelcastInstance() {
		ClientConfig config = new ClientConfig();
		config.getGroupConfig().setName("dev").setPassword("dev-pass");
		config.getNetworkConfig().addAddress("192.168.99.100:5701");
		SerializerConfig sc = new SerializerConfig()
				.setClassName("com.hazelcast.hibernate.serialization.Hibernate5CacheEntrySerializer")
//			    .setImplementation(new Hibernate5CacheEntrySerializerHook().createSerializer())
			    .setTypeClassName("pl.piomin.services.datagrid.person.model.Person");
		config.getSerializationConfig().addSerializerConfig(sc);
		
		HazelcastInstance instance = HazelcastClient.newHazelcastClient(config);
		return instance;
	}
	
	@Bean  
	public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf){  
	    return hemf.getSessionFactory();  
	}
	
}
