package pl.piomin.services.datagrid.person;

import com.hazelcast.client.config.ClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
        config.getNetworkConfig().addAddress(hazelcastUrl);
        config.setInstanceName("cache-1");
        return config;
    }


}
