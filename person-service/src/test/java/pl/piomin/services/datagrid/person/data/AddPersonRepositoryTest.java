package pl.piomin.services.datagrid.person.data;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.piomin.services.datagrid.person.model.Person;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@SpringBootTest
@Testcontainers
public class AddPersonRepositoryTest {

	protected Logger logger = Logger.getLogger(AddPersonRepositoryTest.class.getName());
	
	@Autowired
	PersonRepository repository;

	@Container
	private static final MySQLContainer MYSQL = new MySQLContainer()
			.withUsername("datagrid")
			.withPassword("datagrid");

	@Container
	private static final GenericContainer HAZELCAST = new GenericContainer("hazelcast/hazelcast:3.12")
			.withExposedPorts(5701);

	@DynamicPropertySource
	static void mysqlProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", MYSQL::getJdbcUrl);
		String url = HAZELCAST.getHost() + ":" + HAZELCAST.getFirstMappedPort();
		registry.add("spring.hazelcast.url", () -> url);
	}

	@Test
	public void add() {
		for (int i = 0; i < 1000; i++) {
			int ix = new Random().nextInt(100000);
			Person p = new Person();
			p.setFirstName("Jan" + ix);
			p.setLastName("Testowy" + ix);
			p.setPesel(new DecimalFormat("0000000").format(ix) + new DecimalFormat("000").format(i%100));
			p.setAge(ix%100);
			p = repository.save(p);	
			logger.info("Add: " + p);
		}

	}

	@Test
	void find() {
		logger.info("find()");
		List<Person> ps = repository.findByPesel("0034919066");
		logger.info("find(): " + ps);
	}
	
}
