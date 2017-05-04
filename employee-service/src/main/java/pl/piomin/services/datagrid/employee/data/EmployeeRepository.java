package pl.piomin.services.datagrid.employee.data;

import org.springframework.data.repository.CrudRepository;

import pl.piomin.services.datagrid.employee.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

	public Employee findByPersonId(Integer personId);
	
}
