package pl.piomin.services.datagrid.person.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import pl.piomin.services.datagrid.person.data.PersonRepository;
import pl.piomin.services.datagrid.person.model.Person;

@RestController
public class PersonController {

	@Autowired
	PersonRepository repository;
	
	@GetMapping("/persons/pesel/{pesel}")
	public List<Person> findByPesel(@PathVariable("pesel") String pesel) {
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
