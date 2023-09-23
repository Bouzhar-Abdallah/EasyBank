package Implementations;

import Objects.Affectation;
import Objects.Employer;
import Objects.Person;
import Services.PersonDAOInterface;
import Utils.DBConnection;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            PreparedStatement stmtEmployer = null;
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
            //emp.setId(generatedKeys.getInt(1));
            try{
                // Create the employer record with the inserted person ID
                String insertEmployerQuery = "insert into employer(daterecrutement,personId) values(?,?)";
                stmtEmployer = connection.prepareStatement(insertEmployerQuery,PreparedStatement.RETURN_GENERATED_KEYS);
                //stmtEmployer.setString(1,"test2");
                stmtEmployer.setDate(1,java.sql.Date.valueOf(emp.getDateRecrutement()));
                // part of the Java Database Connectivity (JDBC) library for working with databases.
                stmtEmployer.setInt(2,emp.getId());
                try{
                    stmtEmployer.executeUpdate();
                    var generatedKeys2 = stmtEmployer.getGeneratedKeys();
                    try {
                        if (generatedKeys2.next()) {
                            emp.setMatricule(generatedKeys2.getInt(1));
                        } else {
                            //rollback transaction
                            connection.rollback();
                            return Optional.empty();
                        }
                    }catch (Exception e){
                        System.out.println("test test" + e.getMessage());
                    }
                    //var generatedKeys2 = stmtEmployer.getGeneratedKeys();
                }catch(SQLException e){
                    connection.rollback();
                    System.out.println("*****   une erreur est servunue   *****");
                    System.out.println(e.getMessage());
                }

            }catch(Exception e){
                System.out.println(e.getClass()+"::"+e.getMessage());
            }

            // Commit the transaction if everything was successful
            connection.commit();
            //end transaction
            return search(emp.getMatricule());
        }catch(Exception e){
            System.out.println(e.getClass()+"::"+e.getMessage());
        }
        return Optional.empty();


    }


    @Override
    public Integer delete(Integer id) {
        String deleteQuery = "delete from person where id = ?";
        try{
            PreparedStatement stmt = connection.prepareStatement(deleteQuery);
            stmt.setInt(1,id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0){
                throw new Exception("deletion failed");
            }else{
                return affectedRows;
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Person> getAll() {
        return null;
    }
    public Optional<Person> search(Integer matricule){
        Employer emp = new Employer();
        String searchQuery = "SELECT " +
                "  person.id, " +
                "  person.nom, " +
                "  person.prenom, " +
                "  person.datenaissance, " +
                "  person.numerotel, " +
                "  person.adresse, " +
                "  person.adressemail, " +
                "  employer.matricule, " +
                "  employer.daterecrutement," +
                "  employer.personid " +
                "FROM " +
                "  person " +
                "   INNER JOIN employer " +
                "   ON person.id = employer.personid " +
                "WHERE" +
                "    employer.matricule = ?" +
                ";";
        try{
        PreparedStatement stmt = connection.prepareStatement(searchQuery);
        stmt.setInt(1,matricule);
            ResultSet result = stmt.executeQuery();
            if(result.next()){
                emp.setId(result.getInt("id"));
                emp.setNom(result.getString("nom"));
                emp.setPrenom(result.getString("prenom"));
                emp.setAdresseEmail(result.getString("adressemail"));
                emp.setAdresse(result.getString("adresse"));
                emp.setMatricule(result.getInt("matricule"));
                emp.setNumeroTel(result.getString("numerotel"));
             /*incomplete*/
            return Optional.of(emp);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }
    public List<Affectation> getAllAffectations(Employer employer){
        return null;
    }
    public Affectation getCurrentAffectation(Employer employer){
        return null;
    }
}
