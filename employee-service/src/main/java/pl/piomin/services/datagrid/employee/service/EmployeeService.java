package pl.piomin.services.datagrid.employee.service;

import com.hazelcast.config.IndexType;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import com.hazelcast.query.impl.PredicateBuilderImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piomin.services.datagrid.employee.data.EmployeeRepository;
import pl.piomin.services.datagrid.employee.model.Employee;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class EmployeeService {

    private Logger logger = Logger.getLogger(EmployeeService.class.getName());

    @Autowired
    EmployeeRepository repository;
    @Autowired
    HazelcastInstance instance;

    IMap<Integer, Employee> map;

    @PostConstruct
    public void init() {
        map = instance.getMap("employee");
        map.addIndex(IndexType.HASH, "company");
        logger.info("Employees cache: " + map.size());
    }

    @SuppressWarnings("rawtypes")
    public Employee findByPersonId(Integer personId) {
        PredicateBuilder.EntryObject eo = new PredicateBuilderImpl().getEntryObject();
        Predicate predicate = eo.get("personId").equal(personId);
        logger.info("Employee cache find");
        Collection<Employee> ps = map.values(predicate);
        logger.info("Employee cached: " + ps);
        Optional<Employee> e = ps.stream().findFirst();
        if (e.isPresent())
            return e.get();
        logger.info("Employee cache find");
        Employee emp = repository.findByPersonId(personId);
        logger.info("Employee: " + emp);
        map.put(emp.getId(), emp);
        return emp;
    }

    @SuppressWarnings("rawtypes")
    public List<Employee> findByCompany(String company) {
        PredicateBuilder.EntryObject eo = new PredicateBuilderImpl().getEntryObject();
        Predicate predicate = eo.get("company").equal(company);
        logger.info("Employees cache find");
        Collection<Employee> ps = map.values(predicate);
        logger.info("Employees cache size: " + ps.size());
        if (ps.size() > 0) {
            return new ArrayList<>(ps);
        }
        logger.info("Employees find");
        List<Employee> e = repository.findByCompany(company);
        logger.info("Employees size: " + e.size());
        e.parallelStream().forEach(it -> {
            map.putIfAbsent(it.getId(), it);
        });
        return e;
    }

    public Employee findById(Integer id) {
        Employee e = map.get(id);
        if (e != null)
            return e;
        e = repository.findById(id).orElseThrow();
        map.put(id, e);
        return e;
    }

    public Employee add(Employee e) {
        e = repository.save(e);
        map.put(e.getId(), e);
        return e;
    }

}
