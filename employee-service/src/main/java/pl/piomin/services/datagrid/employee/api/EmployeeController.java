package pl.piomin.services.datagrid.employee.api;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pl.piomin.services.datagrid.employee.model.Employee;
import pl.piomin.services.datagrid.employee.service.EmployeeService;

@RestController
public class EmployeeController {

    private Logger logger = Logger.getLogger(EmployeeController.class.getName());

    @Autowired
    EmployeeService service;

    @GetMapping("/employees/person/{id}")
    public Employee findByPersonId(@PathVariable("id") Integer personId) {
        logger.info(String.format("findByPersonId(%d)", personId));
        return service.findByPersonId(personId);
    }

    @GetMapping("/employees/company/{company}")
    public List<Employee> findByCompany(@PathVariable("company") String company) {
        logger.info(String.format("findByCompany(%s)", company));
        return service.findByCompany(company);
    }

    @GetMapping("/employees/{id}")
    public Employee findById(@PathVariable("id") Integer id) {
        logger.info(String.format("findById(%d)", id));
        return service.findById(id);
    }

    @PostMapping("/employees")
    public Employee add(@RequestBody Employee emp) {
        logger.info(String.format("add(%s)", emp));
        return service.add(emp);
    }

}
