package pl.piomin.services.datagrid.person;

import com.hazelcast.client.ClientConfig;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class PersonApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonApplication.class, args);
	}
	
	@Bean
	HazelcastInstance hazelcastInstance() {
		ClientConfig config = new ClientConfig();
		config.getGroupConfig().setName("dev").setPassword("dev-pass");
		// TODO - fixme
//		config.getNetworkConfig().addAddress("192.168.99.100");
//		config.setInstanceName("cache-1");
		HazelcastInstance instance = HazelcastClient.newHazelcastClient(config);
		return instance;
	}

	// TODO - fixme
//	@Bean
//	CacheManager cacheManager() {
//		return new HazelcastCacheManager(hazelcastInstance());
//	}
		
}
