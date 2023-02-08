package pl.piomin.services.datagrid.person.data;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.piomin.services.datagrid.person.model.Person;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

//@SpringBootTest
//@Testcontainers
public class AddPersonRepositoryTest {

	protected Logger logger = Logger.getLogger(AddPersonRepositoryTest.class.getName());
	
	@Autowired
	PersonRepository repository;

	@Container
	private static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer()
			.withUsername("datagrid")
			.withPassword("datagrid");

//	@BeforeAll
	public static void init() {
		System.setProperty("spring.datasource.url", MY_SQL_CONTAINER.getJdbcUrl());
	}

//	@Test
	public void add() {
		for (int i = 0; i < 1000000; i++) {
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

//	@Test
	void find() {
		logger.info("find()");
		List<Person> ps = repository.findByPesel("0034919066");
		logger.info("find(): " + ps);
	}
	
}
