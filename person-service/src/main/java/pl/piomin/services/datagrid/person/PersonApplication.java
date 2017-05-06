package pl.piomin.services.datagrid.person;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;

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
		config.getNetworkConfig().addAddress("192.168.99.100");
		config.setInstanceName("cache-1");
		HazelcastInstance instance = HazelcastClient.newHazelcastClient(config);
		return instance;
	}
	
	@Bean
	CacheManager cacheManager() {
		return new HazelcastCacheManager(hazelcastInstance());
	}
		
}
