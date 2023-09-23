package Implementations;

import Objects.Client;
import Objects.Person;
import Services.PersonDAOInterface;
import Utils.DBConnection;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class ClientDAO implements PersonDAOInterface {
    private Connection connection;
    public ClientDAO(){
        Connection connection = DBConnection.getDBConnection();
    }
    @Override
    public Optional<Person> create(Person person) {
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        return null;
    }

    @Override
    public List<Person> getAll() {
        return null;
    }
    public Client search(String code){
        return null;
    }
}
