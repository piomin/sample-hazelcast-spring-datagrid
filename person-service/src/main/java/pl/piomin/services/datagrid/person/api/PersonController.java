package pl.piomin.services.datagrid.person.api;

import java.util.List;
import java.util.logging.Logger;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import pl.piomin.services.datagrid.person.data.PersonRepository;
import pl.piomin.services.datagrid.person.model.Person;

@RestController
public class PersonController {

    protected Logger logger = Logger.getLogger(PersonController.class.getName());

    @Autowired
    PersonRepository repository;
    @Autowired
    CacheManager manager;

    @PostConstruct
    public void init() {
        logger.info("Cache manager: " + manager);
        logger.info("Cache manager names: " + manager.getCacheNames());
    }

    @GetMapping("/persons/pesel/{pesel}")
    public List<Person> findByPesel(@PathVariable("pesel") String pesel) {
        return repository.findByPesel(pesel);
    }

    @GetMapping("/persons/{id}")
    public Person findById(@PathVariable("id") Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @GetMapping("/persons")
    public List<Person> findAll() {
        return (List<Person>) repository.findAll();
    }
}
