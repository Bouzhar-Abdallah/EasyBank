package Implementations;

import Objects.Affectation;
import Objects.Employer;
import Objects.Person;
import Services.PersonDAOInterface;
import Utils.DBConnection;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class EmployerDAO implements PersonDAOInterface {
    private Connection connection;
    public EmployerDAO(){
        connection = DBConnection.getDBConnection();
    }
    @Override
    public Optional<Person> create(Person person) {
        Employer emp = (Employer)person;
        try{
            if (emp == null)
                throw new Exception("*****   Impossible d'ajouter un employee vide   *****");

            //start transaction
            connection.setAutoCommit(false);
            String insertPersonQuery = "Insert into person(nom,prenom,datenaissance,numeroTel,adresse,adressemail) values(?,?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(insertPersonQuery,PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, emp.getNom());
            stmt.setString(2, emp.getPrenom());
            stmt.setDate(3,java.sql.Date.valueOf(emp.getDateNaissance()));
            stmt.setString(4, emp.getNumeroTel());
            stmt.setString(5, emp.getAdresse());
            stmt.setString(6, emp.getAdresseEmail());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0){
                //rollback transaction
                connection.rollback();
                return Optional.empty();
            }

            //get the inserted person id

            try (var generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    emp.setId(generatedKeys.getInt(1));
                    //Integer insertedPersonId = generatedKeys.getInt(1);
                } else {
                    //rollback transaction
                    connection.rollback();
                    return Optional.empty();
                }
            }
            try{
                // Create the employer record with the inserted person ID
                String insertEmployerQuery = "insert into employer(matricule,daterecrutement,personId) values(?,?,?)";
                PreparedStatement stmtEmployer = connection.prepareStatement(insertEmployerQuery);
                stmtEmployer.setString(1,"xxxx");
                stmtEmployer.setDate(2,java.sql.Date.valueOf(emp.getDateRecrutement()));
                // part of the Java Database Connectivity (JDBC) library for working with databases.
                stmtEmployer.setInt(3,emp.getId());
                try{
                    stmtEmployer.executeUpdate();
                }catch(Exception e){
                    connection.rollback();
                    System.out.println("*****   une erreur est servunue   *****");
                }

            }catch(Exception e){
                System.out.println(e.getClass()+"::"+e.getMessage());
            }

            // Commit the transaction if everything was successful
            connection.commit();
            return Optional.of(emp);
            //end transaction
        }catch(Exception e){
            System.out.println(e.getClass()+"::"+e.getMessage());
        }
        return Optional.empty();


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
