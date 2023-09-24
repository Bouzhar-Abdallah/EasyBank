package Services;

import Objects.Person;

import java.util.Optional;

public interface ClientDAOInterface {
    Optional<Person> update(Person person);
    Optional<Person> create(Person person);
}
