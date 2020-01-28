package pl.piomin.services.employee.api;

import java.util.List;

import pl.piomin.services.employee.data.EmployeeRepository;
import pl.piomin.services.employee.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	private EmployeeRepository repository;

	EmployeeController(EmployeeRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/person/{id}")
	public Employee findByPersonId(@PathVariable("id") Integer personId) {
		logger.info("findByPersonId({})", personId);
		return repository.findByPersonId(personId);
	}
	
	@GetMapping("/company/{company}")
	public List<Employee> findByCompany(@PathVariable("company") String company) {
		logger.info(String.format("findByCompany({})", company));
		return repository.findByCompany(company);
	}

	@GetMapping("/company/{company}/position/{position}")
	public List<Employee> findByCompanyAndPosition(@PathVariable("company") String company, @PathVariable("position") String position) {
		logger.info(String.format("findByCompany({}, {})", company, position));
		return repository.findByCompanyAndPosition(company, position);
	}
	
	@GetMapping("/{id}")
	public Employee findById(@PathVariable("id") Long id) {
		logger.info("findById({})", id);
		return repository.findById(id).get();
	}

	@GetMapping("/salary/{salary}")
	public List<Employee> findBySalaryGreaterThan(@PathVariable("salary") int salary) {
		logger.info(String.format("findBySalaryGreaterThan({})", salary));
		return repository.findBySalaryGreaterThan(salary);
	}

	@PostMapping
	public Employee add(@RequestBody Employee emp) {
		logger.info("add({})", emp);
		return repository.save(emp);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		logger.info("delete({})", id);
		repository.deleteById(id);
	}

}
