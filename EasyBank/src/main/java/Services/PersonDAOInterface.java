package Services;

import Objects.Person;

import java.util.List;
import java.util.Optional;

public interface PersonDAOInterface {
    Optional<Person> create(Person person);
    Integer delete(Integer id);
    List<Person> getAll();
}
