package pl.piomin.services.datagrid.person;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class PersonApplication {

	private static final Logger LOG = LoggerFactory.getLogger(PersonApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PersonApplication.class, args);
	}

	@Value("${spring.hazelcast.url:localhost:5701}")
	private String hazelcastUrl;

	@Bean
	ClientConfig clientConfig() {
		LOG.info("Connecting Hazelcast: url={}", hazelcastUrl);
		ClientConfig config = new ClientConfig();
//		config.getGroupConfig().setName("dev").setPassword("dev-pass");
		config.getNetworkConfig().addAddress(hazelcastUrl);
		config.setInstanceName("cache-1");
		return config;
//		HazelcastInstance instance = HazelcastClient.newHazelcastClient(config);
//		return instance;
	}

//	@Bean
//	CacheManager cacheManager() {
//		return new HazelcastCacheManager(hazelcastInstance());
//	}
		
}
