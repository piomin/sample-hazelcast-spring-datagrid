package pl.piomin.services.datagrid.person.api;

import java.util.List;

import com.hazelcast.client.HazelcastClientOfflineException;
import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import pl.piomin.services.datagrid.person.data.PersonRepository;
import pl.piomin.services.datagrid.person.model.Person;

@RestController
public class PersonController {

    protected Logger logger = LoggerFactory.getLogger(PersonController.class.getName());

    @Autowired
    PersonRepository repository;
    @Autowired
    CacheManager manager;

    @PostConstruct
    public void init() {
        try {
            logger.info("Cache manager: " + manager);
            logger.info("Cache manager names: " + manager.getCacheNames());
        } catch (HazelcastClientOfflineException e) {
            logger.error("No Hazelcast connection", e);
        }
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
