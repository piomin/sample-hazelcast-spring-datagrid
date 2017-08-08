package pl.piomin.services.datagrid.employee.data;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.piomin.services.datagrid.employee.model.Employee;


@SpringBootTest
@RunWith(SpringRunner.class)
public class AddEmployeeRepositoryTest {

	protected Logger logger = Logger.getLogger(AddEmployeeRepositoryTest.class.getName());
	
	@Autowired
	EmployeeRepository repository;
	
	@Test
	public void add() {
		for (int i = 1; i < 2000000; i++) {
			int ix = new Random().nextInt(100);
			Employee e = new Employee();
			e.setPersonId(i);
			e.setCompany("TEST" + new DecimalFormat("000").format(ix));
			e = repository.save(e);	
			logger.info("Add: " + e);
		}
	}
	
}
