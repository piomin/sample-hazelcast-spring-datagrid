package pl.piomin.services.datagrid.employee.data;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.piomin.services.datagrid.employee.model.Employee;


//@SpringBootTest
//@Testcontainers
public class AddEmployeeRepositoryTest {

	protected Logger logger = Logger.getLogger(AddEmployeeRepositoryTest.class.getName());
	
	@Autowired
	EmployeeRepository repository;

	@Container
	private static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer()
			.withUsername("datagrid")
			.withPassword("datagrid");

//	@BeforeAll
	public static void init() {
		System.setProperty("spring.datasource.url", MY_SQL_CONTAINER.getJdbcUrl());
	}

//	@Test
	void add() {
		for (int i = 0; i < 2000000; i++) {
			int ix = new Random().nextInt(100);
			Employee e = new Employee();
			e.setPersonId(i);
			e.setCompany("TEST" + new DecimalFormat("000").format(ix));
			e = repository.save(e);	
			logger.info("Add: " + e);
		}
	}
	
}
