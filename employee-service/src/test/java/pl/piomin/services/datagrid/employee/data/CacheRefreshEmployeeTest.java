package pl.piomin.services.datagrid.employee.data;

import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import pl.piomin.services.datagrid.employee.model.Employee;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
@EnableAutoConfiguration(exclude = {HazelcastAutoConfiguration.class})
public class CacheRefreshEmployeeTest {

	protected Logger logger = Logger.getLogger(CacheRefreshEmployeeTest.class.getName());
	
	@Autowired
	EmployeeRepository repository;
	
	TestRestTemplate template = new TestRestTemplate();
	
	@Test
	public void testModifyAndRefresh() {
		Employee e = template.getForObject("http://localhost:3333/employees/{id}", Employee.class, 1);
		Employee eMod = repository.findOne(1);
		eMod.setPersonId(eMod.getPersonId()+1);
		repository.save(eMod);
		Employee e2 = template.getForObject("http://localhost:3333/employees/{id}", Employee.class, 1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Assert.assertNotEquals(e.getPersonId(), e2.getPersonId());
	}
}
