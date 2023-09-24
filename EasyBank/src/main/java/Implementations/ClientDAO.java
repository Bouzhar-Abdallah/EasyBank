package Implementations;

import Objects.Client;
import Objects.Affectation;
import Objects.Employer;
import Objects.Person;
import Services.ClientDAOInterface;
import Services.PersonDAOInterface;
import Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ClientDAO extends PersonDAO implements ClientDAOInterface {
    public ClientDAO(){connection = DBConnection.getDBConnection();}
    @Override
    public Optional<Person> create(Person person) {
        Client client = (Client) person;
        try {
            if (client == null)
                throw new Exception("*****   Impossible d'ajouter un client vide   *****");

            //start transaction
            connection.setAutoCommit(false);
            String insertPersonQuery = "Insert into person(nom,prenom,datenaissance,numeroTel,adresse,adressemail) values(?,?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(insertPersonQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setDate(3, java.sql.Date.valueOf(client.getDateNaissance()));
            stmt.setString(4, client.getNumeroTel());
            stmt.setString(5, client.getAdresse());
            stmt.setString(6, client.getAdresseEmail());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                //rollback transaction
                connection.rollback();
                return Optional.empty();
            }

            //get the inserted person id
            PreparedStatement stmtEmployer ;
            try (var generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    client.setId(generatedKeys.getInt(1));
                    //Integer insertedPersonId = generatedKeys.getInt(1);
                } else {
                    //rollback transaction
                    connection.rollback();
                    return Optional.empty();
                }
            }
            //client.setId(generatedKeys.getInt(1));
            try {
                // Create the employer record with the inserted person ID
                String insertClientQuery = "insert into client(personId) values(?)";
                stmtEmployer = connection.prepareStatement(insertClientQuery, PreparedStatement.RETURN_GENERATED_KEYS);
                //stmtEmployer.setString(1,"test2");

                // part of the Java Database Connectivity (JDBC) library for working with databases.
                stmtEmployer.setInt(1, client.getId());
                try {
                    stmtEmployer.executeUpdate();
                    var generatedKeys2 = stmtEmployer.getGeneratedKeys();
                    try {
                        if (generatedKeys2.next()) {
                            client.setCode(generatedKeys2.getInt(1));
                        } else {
                            //rollback transaction
                            connection.rollback();
                            return Optional.empty();
                        }
                    } catch (Exception e) {
                        System.out.println("test test" + e.getMessage());
                    }
                    //var generatedKeys2 = stmtEmployer.getGeneratedKeys();
                } catch (SQLException e) {
                    connection.rollback();
                    System.out.println("*****   une erreur est servunue   *****");
                    System.out.println(e.getMessage());
                }

            } catch (Exception e) {
                System.out.println(e.getClass() + "::" + e.getMessage());
            }

            // Commit the transaction if everything was successful
            connection.commit();
            //end transaction
            return searchByClientCode(client.getCode());
        } catch (Exception e) {
            System.out.println(e.getClass() + "::" + e.getMessage());
        }
        return Optional.empty();


    }
    @Override
    public Optional<Person> update(Person person){
        Client clientToUpdate = (Client) person;
        try {
            connection.setAutoCommit(false);
            String updatePersonQuery = "update person set nom = ? , prenom = ? , datenaissance = ?, numeroTel = ? , adresse = ? , adressemail =? where id = ?";
            PreparedStatement stmtPerson = connection.prepareStatement(updatePersonQuery);
            stmtPerson.setString(1, clientToUpdate.getNom());
            stmtPerson.setString(2, clientToUpdate.getPrenom());
            stmtPerson.setDate(3, java.sql.Date.valueOf(clientToUpdate.getDateNaissance()));
            stmtPerson.setString(4, clientToUpdate.getNumeroTel());
            stmtPerson.setString(5, clientToUpdate.getAdresse());
            stmtPerson.setString(6, clientToUpdate.getAdresseEmail());
            stmtPerson.setInt(7, clientToUpdate.getId());
            int rowsUpdated = stmtPerson.executeUpdate();
            if (rowsUpdated == 0) {
                connection.rollback();
            }

            String updateEmpQuery = "update client set updatedat = CURRENT_TIMESTAMP where code = ? ";
            PreparedStatement stmtEmp = connection.prepareStatement(updateEmpQuery);
            stmtEmp.setInt(1,clientToUpdate.getCode());
            rowsUpdated = stmtEmp.executeUpdate();
            if (rowsUpdated == 0) {
                connection.rollback();
            }
            connection.commit();
            //connection.close();
            //end transaction
            return searchByClientCode(clientToUpdate.getCode());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    public List<Person> getAll() {
        return getAll("client");
    }
    public Client search(String code){
        return null;
    }

    public Optional<Person> searchByClientCode(Integer clientCode) {
        Client client = new Client();
        String searchQuery = "SELECT " +
                "  person.id, " +
                "  person.nom, " +
                "  person.prenom, " +
                "  person.datenaissance, " +
                "  person.numerotel, " +
                "  person.adresse, " +
                "  person.adressemail, " +
                "  client.code " +
                "FROM " +
                "  person " +
                "   INNER JOIN client " +
                "   ON person.id = client.personid " +
                "WHERE" +
                "    client.code = ?" +
                ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(searchQuery);
            stmt.setInt(1, clientCode);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                client.setId(result.getInt("id"));
                client.setNom(result.getString("nom"));
                client.setPrenom(result.getString("prenom"));
                client.setAdresseEmail(result.getString("adressemail"));
                client.setAdresse(result.getString("adresse"));
                client.setNumeroTel(result.getString("numerotel"));
                client.setDateNaissance(result.getDate("datenaissance").toLocalDate());
                client.setCode(result.getInt("code"));
                /*incomplete*/
                return Optional.of(client);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }
}
