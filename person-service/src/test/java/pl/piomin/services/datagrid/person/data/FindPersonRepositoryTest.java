package pl.piomin.services.datagrid.person.data;

import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.piomin.services.datagrid.person.model.Person;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FindPersonRepositoryTest {

	protected Logger logger = Logger.getLogger(FindPersonRepositoryTest.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	@Test
	public void find() {
		logger.info("find()");
		List<Person> ps = repository.findByPesel("0034919066");
		logger.info("find(): " + ps);
	}
	
}
