package pl.piomin.services.datagrid.employee.api;

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
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import com.hazelcast.query.Predicates;

import pl.piomin.services.datagrid.employee.data.EmployeeRepository;
import pl.piomin.services.datagrid.employee.model.Employee;

@RestController
public class EmployeeController {

	protected Logger logger = Logger.getLogger(EmployeeController.class.getName());
	
	@Autowired
	EmployeeRepository repository;
	
	@Autowired
	HazelcastInstance instance;
	IMap<Integer, Employee> map;
	
	@PostConstruct
	public void init() {
		map = instance.getMap("employee");
		logger.info("Persons cache: " + map.size());

	}
	
	@GetMapping("/employees/person/{id}")
	public Employee findByPersonId(@PathVariable("id") Integer personId) {
		EntryObject e = new PredicateBuilder().getEntryObject();
//		Predicate predicate = e.get("pesel").is(pesel);
		Predicate predicate = Predicates.equal("personId", personId);
//		Predicate predicate = e.is( "active" ).and( e.get( "age" ).lessThan( 30 ) );
		Collection<Employee> ps = map.values(predicate);
		
//		List<Person> ps = (List<Person>) map.values();
//		Person p = ps.get(0);
		return repository.findByPersonId(personId);
	}
	
	@GetMapping("/employees/{id}")
	public Employee findById(@PathVariable("id") Integer id) {
		Employee e = repository.findOne(id);
		map.put(id, e);
		return e;
	}
	
	@GetMapping("/employees")
	public List<Employee> findAll() {
		return (List<Employee>) repository.findAll();
	}
}
