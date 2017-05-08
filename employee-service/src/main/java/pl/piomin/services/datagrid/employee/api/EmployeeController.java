package pl.piomin.services.datagrid.employee.api;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.Predicate;
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
		map.addIndex("company", true);
		logger.info("Employees cache: " + map.size());

	}
	
	@GetMapping("/employees/person/{id}")
	public Employee findByPersonId(@PathVariable("id") Integer personId) {
//		EntryObject e = new PredicateBuilder().getEntryObject();
//		Predicate predicate = e.get("pesel").is(pesel);
		Predicate predicate = Predicates.equal("personId", personId);
//		Predicate predicate = e.is( "active" ).and( e.get( "age" ).lessThan( 30 ) );
		Collection<Employee> ps = map.values(predicate);
		logger.info("Employees: " + ps);
		Optional<Employee> e = ps.stream().findFirst();
		if (e.isPresent())
			return e.get();
//		List<Person> ps = (List<Person>) map.values();
//		Person p = ps.get(0);
		return repository.findByPersonId(personId);
	}
	
	@GetMapping("/employees/company/{company}")
	public List<Employee> findByCompany(@PathVariable("company") String company) {
		Predicate predicate = Predicates.equal("company", company);
		logger.info("Employees cache find");
		Collection<Employee> ps = map.values(predicate);
		logger.info("Employees cache size: " + ps.size());
		if (ps.size() > 0) {
			return ps.stream().collect(Collectors.toList());
		}
		logger.info("Employees find");
		List<Employee> e = repository.findByCompany(company);
		logger.info("Employees size: " + e.size());
		e.parallelStream().forEach(it -> {
			map.putIfAbsent(it.getId(), it);
		});
		return e;
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
