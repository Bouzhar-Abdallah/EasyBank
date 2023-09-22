package Implementations;

import Objects.Affectation;
import Objects.Employer;
import Objects.Person;
import Services.PersonDAOInterface;
import Utils.DBConnection;

import java.sql.Connection;
import java.util.List;

public class EmployerDAO implements PersonDAOInterface {
    private Connection connection;
    public EmployerDAO(){
        Connection connection = DBConnection.getDBConnection();
    }
    @Override
    public Person create(Person person) {
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
    public Employer search(String matricule){
        return null;
    }
    public List<Affectation> getAllAffectations(Employer employer){
        return null;
    }
    public Affectation getCurrentAffectation(Employer employer){
        return null;
    }
}
