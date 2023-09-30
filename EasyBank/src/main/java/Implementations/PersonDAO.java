package Implementations;

import Objects.Employer;
import Objects.Person;
import Services.PersonDAOInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

abstract class PersonDAO implements PersonDAOInterface {
    protected Connection connection;


    @Override
    public Integer delete(Integer id) {
        String deleteQuery = "delete from person where id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(deleteQuery);
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("deletion failed");
            } else {
                return affectedRows;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Person> getAll(String type) {
        String getAllQuery = getGetAllQuery(type);


        List<Person> persons = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(getAllQuery);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                Employer emp = new Employer();
                emp.setId(result.getInt("id"));
                emp.setNom(result.getString("nom"));
                emp.setPrenom(result.getString("prenom"));
                emp.setAdresseEmail(result.getString("adressemail"));
                emp.setAdresse(result.getString("adresse"));
                emp.setNumeroTel(result.getString("numerotel"));
                emp.setDateNaissance(result.getDate("datenaissance").toLocalDate());
                if (type.equals("employer")) {
                    emp.setMatricule(result.getInt("matricule"));
                    emp.setDateRecrutement(result.getDate("daterecrutement").toLocalDate());
                }
                persons.add(emp);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return persons;
    }

    private String getGetAllQuery(String type) {
        String getAllQuery;
        if (type.equals("employer")) {
            getAllQuery = "SELECT " +
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
                    "   ON person.id = employer.personid ;";
        } else {
            getAllQuery = "SELECT " +
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
                    "   ON person.id = client.personid ;";
        }
        return getAllQuery;
    }


    public Optional<Person> searchByNom(String nom) {
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
                "    person.nom = ?" +
                ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(searchQuery);
            stmt.setString(1, nom);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                emp.setId(result.getInt("id"));
                emp.setNom(result.getString("nom"));
                emp.setPrenom(result.getString("prenom"));
                emp.setAdresseEmail(result.getString("adressemail"));
                emp.setAdresse(result.getString("adresse"));
                emp.setMatricule(result.getInt("matricule"));
                emp.setNumeroTel(result.getString("numerotel"));
                emp.setDateRecrutement(result.getDate("daterecrutement").toLocalDate());
                emp.setDateNaissance(result.getDate("datenaissance").toLocalDate());
                /*incomplete*/
                return Optional.of(emp);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    public Optional<Person> searchByDateNaissance(LocalDate datenaissance) {
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
                "    person.datenaissance = ?" +
                ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(searchQuery);
            stmt.setDate(1, java.sql.Date.valueOf(datenaissance));
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                emp.setId(result.getInt("id"));
                emp.setNom(result.getString("nom"));
                emp.setPrenom(result.getString("prenom"));
                emp.setAdresseEmail(result.getString("adressemail"));
                emp.setAdresse(result.getString("adresse"));
                emp.setMatricule(result.getInt("matricule"));
                emp.setNumeroTel(result.getString("numerotel"));
                emp.setDateRecrutement(result.getDate("daterecrutement").toLocalDate());
                emp.setDateNaissance(result.getDate("datenaissance").toLocalDate());
                /*incomplete*/
                return Optional.of(emp);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    public Optional<Person> searchByNumeroTel(String numeroTel) {
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
                "    person.numerotel = ?" +
                ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(searchQuery);
            stmt.setDate(1, java.sql.Date.valueOf(numeroTel));
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                emp.setId(result.getInt("id"));
                emp.setNom(result.getString("nom"));
                emp.setPrenom(result.getString("prenom"));
                emp.setAdresseEmail(result.getString("adressemail"));
                emp.setAdresse(result.getString("adresse"));
                emp.setMatricule(result.getInt("matricule"));
                emp.setNumeroTel(result.getString("numerotel"));
                emp.setDateRecrutement(result.getDate("daterecrutement").toLocalDate());
                emp.setDateNaissance(result.getDate("datenaissance").toLocalDate());
                /*incomplete*/
                return Optional.of(emp);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }
}
