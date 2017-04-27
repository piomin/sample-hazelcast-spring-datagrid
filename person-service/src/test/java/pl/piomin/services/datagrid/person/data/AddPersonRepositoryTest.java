package pl.piomin.services.datagrid.person.data;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.piomin.services.datagrid.person.model.Person;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddPersonRepositoryTest {

	protected Logger logger = Logger.getLogger(AddPersonRepositoryTest.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	@Test
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
	
}
