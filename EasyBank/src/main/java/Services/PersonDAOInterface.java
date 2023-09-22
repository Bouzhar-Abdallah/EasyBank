package Services;

import Objects.Person;

import java.util.List;

public interface PersonDAOInterface {
    Person create(Person person);
    Integer delete(Integer id);
    List<Person> getAll();
}
