package pl.piomin.services.datagrid.person;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import pl.piomin.services.datagrid.person.model.Person;

@SpringBootApplication
@EnableAutoConfiguration(exclude={HibernateJpaAutoConfiguration.class})
//@EnableHazelcastRepositories
public class PersonApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonApplication.class, args);
	}

//	@Bean
//	HazelcastInstance hazelcastInstance() {
//		ClientConfig config = new ClientConfig();
//		config.getGroupConfig().setName("dev").setPassword("dev-pass");
//		config.getNetworkConfig().addAddress("192.168.99.100:5701");
//		HazelcastInstance instance =  HazelcastClient.newHazelcastClient(config);
//		return instance;
//	}

	@Bean
	DataSource datasource() {
		DataSource ds = new DriverManagerDataSource("jdbc:mysql://192.168.99.100:33306/datagrid?useSSL=false", "datagrid", "datagrid");
		return ds;
	}
	
	@Bean
	@Primary
	LocalSessionFactoryBean factory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(datasource());
		Properties properties = new Properties();
		properties.put("hibernate.cache.region.factory_class", "com.hazelcast.hibernate.HazelcastLocalCacheRegionFactory");
//		properties.put("hibernate.cache.hazelcast.instance_name", "dev");
		properties.put("hibernate.cache.hazelcast.use_native_client", "true");
		properties.put("hibernate.cache.hazelcast.native_client_address", "192.168.99.100");
		properties.put("hibernate.cache.hazelcast.native_client_group", "dev");
		properties.put("hibernate.cache.hazelcast.native_client_password", "dev-pass");
		properties.put("hibernate.cache.use_second_level_cache", "true");
		sessionFactory.setHibernateProperties(properties);
		return sessionFactory;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean customerEntityManagerFactory(
	        EntityManagerFactoryBuilder builder) {
	    return builder
	            .dataSource(datasource())
	            .packages(Person.class)
	            .persistenceUnit("default")
	            .build();
	}
	
//	@Bean
//	public KeyValueOperations keyValueTemplate() {
//		return new KeyValueTemplate(new HazelcastKeyValueAdapter(hazelcastInstance()));
//	}

//	@Bean
//	public HazelcastKeyValueAdapter hazelcastKeyValueAdapter(HazelcastInstance hazelcastInstance) {
//		return new HazelcastKeyValueAdapter(hazelcastInstance);
//	}

}
