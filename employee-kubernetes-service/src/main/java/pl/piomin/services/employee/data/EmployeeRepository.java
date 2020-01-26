package pl.piomin.services.employee.data;


import java.util.List;

import pl.piomin.services.employee.model.Employee;

import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

	Employee findByPersonId(Integer personId);
	List<Employee> findByCompany(String company);
	
}
