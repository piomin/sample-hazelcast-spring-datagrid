package pl.piomin.services.employee.data;


import java.util.List;

import pl.piomin.services.employee.model.Employee;

import org.springframework.data.hazelcast.repository.HazelcastRepository;

public interface EmployeeRepository extends HazelcastRepository<Employee, Long> {

	Employee findByPersonId(Integer personId);
	List<Employee> findByCompany(String company);
	List<Employee> findByCompanyAndPosition(String company, String position);
	List<Employee> findBySalaryGreaterThan(int salary);

}
