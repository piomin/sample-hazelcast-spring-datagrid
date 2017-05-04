package pl.piomin.services.datagrid.person.api;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.hibernate.instance.HazelcastAccessor;
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import com.hazelcast.query.Predicates;

import pl.piomin.services.datagrid.person.data.PersonRepository;
import pl.piomin.services.datagrid.person.model.Person;

@RestController
public class PersonController {

	protected Logger logger = Logger.getLogger(PersonController.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	@Autowired
	HazelcastInstance instance;
	IMap<Integer, Person> map;
	
//	@Autowired  
//	   SessionFactory sessionFactory; 
	
	@PostConstruct
	public void init() {
//		HazelcastInstance instance = HazelcastAccessor.getHazelcastInstance(sessionFactory);
		map = instance.getMap("pl.piomin.services.datagrid.person.model.Person");
//		map.addIndex("pesel", false);
		logger.info("Persons cache: " + map.size());

	}
	
	@GetMapping("/persons/pesel/{pesel}")
	public List<Person> findByPesel(@PathVariable("pesel") String pesel) {
		EntryObject e = new PredicateBuilder().getEntryObject();
		Predicate predicate = e.get("pesel").is(pesel);
		Predicate peselPredicate = Predicates.equal("pesel", pesel);
//		Predicate predicate = e.is( "active" ).and( e.get( "age" ).lessThan( 30 ) );
		Collection<Person> ps = map.values(predicate);
		
//		List<Person> ps = (List<Person>) map.values();
//		Person p = ps.get(0);
		return repository.findByPesel(pesel);
	}
	
	@GetMapping("/persons/{id}")
	public Person findById(@PathVariable("id") Integer id) {
		return repository.findOne(id);
	}
	
	@GetMapping("/persons")
	public List<Person> findAll() {
		return (List<Person>) repository.findAll();
	}
}
