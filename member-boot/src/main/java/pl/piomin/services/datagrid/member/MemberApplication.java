package pl.piomin.services.datagrid.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@SpringBootApplication
public class MemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberApplication.class, args);
	}

	@Bean
	HazelcastInstance hazelcastInstance() {
//		Config config = new Config();
		HazelcastInstance instance = Hazelcast.newHazelcastInstance();
		return instance;
	}
	
}
