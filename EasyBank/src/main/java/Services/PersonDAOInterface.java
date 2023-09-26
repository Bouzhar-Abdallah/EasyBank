package Services;

import Objects.Person;

import java.util.List;


public interface PersonDAOInterface {
    Integer delete(Integer id);
    List<Person> getAll(String type);
    /*Optional<Person> update(Person person);
    Optional<Person> create(Person person);*/
}
