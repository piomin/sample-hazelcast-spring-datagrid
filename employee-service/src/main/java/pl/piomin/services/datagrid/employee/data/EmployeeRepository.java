package pl.piomin.services.datagrid.employee.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.piomin.services.datagrid.employee.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    public Employee findByPersonId(Integer personId);

    public List<Employee> findByCompany(String company);

}
