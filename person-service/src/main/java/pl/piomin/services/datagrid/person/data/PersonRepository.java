package pl.piomin.services.datagrid.person.data;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import pl.piomin.services.datagrid.person.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {

    @Cacheable("findByPesel")
    public List<Person> findByPesel(String pesel);

}
