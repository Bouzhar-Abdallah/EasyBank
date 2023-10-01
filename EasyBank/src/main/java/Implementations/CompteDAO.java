package Implementations;

import Enums.Etat_enum;
import Objects.*;
import Services.CompteDAOInterface;
import Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompteDAO implements CompteDAOInterface {
    protected Connection connection;
    public CompteDAO() {
        connection = DBConnection.getDBConnection();
    }
    @Override
    public Integer delete(Long numero) {
        String deleteQuery = "delete from compte where numero = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(deleteQuery);
            stmt.setLong(1, numero);
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
    public List<Compte> getAll(String type) {
        String getAllQuery = getGetAllQuery(type);
        List<Compte> comptes = new ArrayList<>();
        if (type.equals("courant")) {
            try {
                PreparedStatement stmt = connection.prepareStatement(getAllQuery);
                ResultSet result = stmt.executeQuery();
                while (result.next()) {
                    Courant compte = new Courant();
                    compte.setNumero(result.getLong("numero"));
                    compte.setSolde(result.getDouble("solde"));
                    compte.setDateCreation(result.getDate("datecreation").toLocalDate());
                    compte.setDecouvert(result.getDouble("decouvert"));
                    Optional<Person> person = new ClientDAO().searchByClientCode(result.getInt("clientcode"));
                    Client client = new Client();
                    if (person.isPresent()){
                        client = (Client)person.get();
                    }
                    compte.setClient(client);

                    Employer employer = new Employer();
                    Optional<Person> optEmp = new EmployerDAO().searchByMatricule(result.getInt("employermatricule"));
                    if (optEmp.isPresent()){
                        employer = (Employer) optEmp.get();
                    }
                    compte.setEmplyer(employer);

                    String etatValue = result.getString("etat");
                    Etat_enum etat = Etat_enum.valueOf(etatValue);
                    compte.setEtat(etat);
                    comptes.add(compte);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            try {
                PreparedStatement stmt = connection.prepareStatement(getAllQuery);
                ResultSet result = stmt.executeQuery();
                while (result.next()) {
                    Epargne compte = new Epargne();
                    compte.setNumero(result.getLong("numero"));
                    compte.setSolde(result.getDouble("solde"));
                    compte.setDateCreation(result.getDate("datecreation").toLocalDate());
                    compte.setTauxInteret(result.getDouble("tauxinteret"));


                    Client client = new Client();
                    Optional<Person> person = new ClientDAO().searchByClientCode(result.getInt("clientcode"));
                    if (person.isPresent()){
                        client = (Client)person.get();
                    }
                    compte.setClient(client);


                    Employer employer = new Employer();
                    Optional<Person> optEmp = new EmployerDAO().searchByMatricule(result.getInt("employermatricule"));
                    if (optEmp.isPresent()){
                        employer = (Employer) optEmp.get();
                    }
                    compte.setEmplyer(employer);

                    String etatValue = result.getString("etat");
                    Etat_enum etat = Etat_enum.valueOf(etatValue);
                    compte.setEtat(etat);

                    comptes.add(compte);
                }
            } catch (SQLException e) {
                // Handle SQLException appropriately, e.g., logging or throwing a custom exception
                e.printStackTrace();
            }

        }
        return comptes;
    }

    private String getGetAllQuery(String type) {
        String getAllQuery;
        if (type.equals("epargne")) {
            getAllQuery = "SELECT " +
                    "  compte.numero, " +
                    "  compte.solde, " +
                    "  compte.datecreation, " +
                    "  compte.etat, " +
                    "  compte.employermatricule, " +
                    "  compte.clientcode, " +
                    "  epargne.tauxinteret, " +
                    "  epargne.comptenumero " +
                    "FROM " +
                    "  compte " +
                    "   INNER JOIN epargne " +
                    "   ON compte.numero = epargne.comptenumero ;";
        } else {
            getAllQuery = "SELECT " +
                    "  compte.numero, " +
                    "  compte.solde, " +
                    "  compte.datecreation, " +
                    "  compte.etat, " +
                    "  compte.employermatricule, " +
                    "  compte.clientcode, " +
                    "  courant.decouvert, " +
                    "  courant.comptenumero " +
                    "FROM " +
                    "  compte " +
                    "   INNER JOIN courant " +
                    "   ON compte.numero = courant.comptenumero ;";
        }
        return getAllQuery;
    }

    public Optional<Compte> findByNumero(Long numero) {

        String Query = "SELECT " +
                "    c.*, " +
                "    CASE WHEN cr.compteNumero IS NOT NULL THEN 'courant' ELSE 'epargne' END AS ACCOUNT_TYPE, " +
                "    cr.decouvert AS DECOUVERT, " +
                "    ep.tauxInteret AS TAUXINTERET " +
                "FROM compte c " +
                "LEFT JOIN courant cr ON c.numero = cr.compteNumero " +
                "LEFT JOIN epargne ep ON c.numero = ep.compteNumero " +
                "where numero = ?; ";
        try {
            PreparedStatement stmt = connection.prepareStatement(Query);
            stmt.setLong(1,numero);
            ResultSet result = stmt.executeQuery();
            if (result.next()){
                if (result.getString("ACCOUNT_TYPE").equals("courant")){
                    Courant courant = new Courant();
                    courant.setNumero(result.getLong("numero"));
                    courant.setDecouvert(result.getDouble("decouvert"));

                    String etatValue = result.getString("etat");
                    Etat_enum etat = Etat_enum.valueOf(etatValue);
                    courant.setEtat(etat);
                    courant.setSolde(result.getDouble("solde"));
                    courant.setDateCreation(result.getDate("datecreation").toLocalDate());
                    Employer emp = new Employer();
                    emp.setMatricule(result.getInt("employermatricule"));
                    Client clt = new Client();
                    clt.setCode(result.getInt("clientcode"));
                    courant.setClient(clt);
                    courant.setEmplyer(emp);
                    return Optional.of(courant);

                }else{
                    Epargne epargne = new Epargne();
                    epargne.setNumero(result.getLong("numero"));
                    epargne.setTauxInteret(result.getDouble("tauxinteret"));
                    String etatValue = result.getString("etat");
                    Etat_enum etat = Etat_enum.valueOf(etatValue);
                    epargne.setEtat(etat);
                    epargne.setSolde(result.getDouble("solde"));
                    epargne.setDateCreation(result.getDate("datecreation").toLocalDate());
                    Employer emp = new Employer();
                    emp.setMatricule(result.getInt("employermatricule"));
                    Client clt = new Client();
                    clt.setCode(result.getInt("clientcode"));
                    epargne.setClient(clt);
                    epargne.setEmplyer(emp);
                    return Optional.of(epargne);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    return Optional.empty();
    }
    public Integer updateStatus(Compte compte){
        String Query = "update compte set etat = ?::etat_enum where numero = ?";
        try{
            PreparedStatement stmt = connection.prepareStatement(Query);
            stmt.setString(1,compte.getEtat().name());
            stmt.setLong(2,compte.getNumero());
            return  stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return 0;
        }
    }
    public Integer updateSolde(Compte compte){
        String Query = "update compte set solde = ? where numero = ?";
        try{
            PreparedStatement stmt = connection.prepareStatement(Query);
            stmt.setDouble(1,compte.getSolde());
            stmt.setLong(2,compte.getNumero());
            return  stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return 0;
        }
    }
    public Integer updateNumero(Compte compte){
        String Query = "update compte set numero = ? where numero = ?";
        try{
            PreparedStatement stmt = connection.prepareStatement(Query);
            stmt.setLong(1,compte.getNumero());
            stmt.setLong(2,compte.getNumero());
            return  stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        return 0;
        }
    }
    public Integer updateDecouvert(Courant courant){
        String Query = "update courant set decouvert = ? where comptenumero = ?";
        try{
            PreparedStatement stmt = connection.prepareStatement(Query);
            stmt.setDouble(1,courant.getDecouvert());
            stmt.setLong(2,courant.getNumero());
            return stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return 0;
        }
    }
    public Integer updateTauxInteret(Epargne epargne){
        String Query = "update epargne set tauxinteret = ? where comptenumero = ?";
        try{
            PreparedStatement stmt = connection.prepareStatement(Query);
            stmt.setDouble(1,epargne.getTauxInteret());
            stmt.setLong(2,epargne.getNumero());
            int count = stmt.executeUpdate();
            return  stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return 0;
        }
    }
    public List<Compte> findByClient(Client client){
        String Query = "SELECT " +
                "    c.*, " +
                "    CASE WHEN cr.compteNumero IS NOT NULL THEN 'courant' ELSE 'epargne' END AS ACCOUNT_TYPE, " +
                "    cr.decouvert AS DECOUVERT, " +
                "    ep.tauxInteret AS TAUXINTERET " +
                "FROM compte c " +
                "LEFT JOIN courant cr ON c.numero = cr.compteNumero " +
                "LEFT JOIN epargne ep ON c.numero = ep.compteNumero " +
                "where clientcode = ?; ";
        List<Compte> comptes = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(Query);
            stmt.setInt(1,client.getCode());
            ResultSet result = stmt.executeQuery();
            while (result.next()){
                if (result.getString("ACCOUNT_TYPE").equals("courant")){
                    Courant courant = new Courant();
                    courant.setNumero(result.getLong("numero"));
                    courant.setDecouvert(result.getDouble("decouvert"));

                    String etatValue = result.getString("etat");
                    Etat_enum etat = Etat_enum.valueOf(etatValue);
                    courant.setEtat(etat);
                    courant.setSolde(result.getDouble("solde"));
                    courant.setDateCreation(result.getDate("datecreation").toLocalDate());
                    Employer emp = new Employer();
                    emp.setMatricule(result.getInt("employermatricule"));
                    Client clt = new Client();
                    clt.setCode(result.getInt("clientcode"));
                    courant.setClient(clt);
                    courant.setEmplyer(emp);
                    comptes.add(courant);

                }else{
                    Epargne epargne = new Epargne();
                    epargne.setNumero(result.getLong("numero"));
                    epargne.setTauxInteret(result.getDouble("tauxinteret"));
                    String etatValue = result.getString("etat");
                    Etat_enum etat = Etat_enum.valueOf(etatValue);
                    epargne.setEtat(etat);
                    epargne.setSolde(result.getDouble("solde"));
                    epargne.setDateCreation(result.getDate("datecreation").toLocalDate());
                    Employer emp = new Employer();
                    emp.setMatricule(result.getInt("employermatricule"));
                    Client clt = new Client();
                    clt.setCode(result.getInt("clientcode"));
                    epargne.setClient(clt);
                    epargne.setEmplyer(emp);
                    comptes.add(epargne);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return comptes;
    }
    public List<Compte> findByStatus(String status){
        String Query = "SELECT " +
                "    c.*, " +
                "    CASE WHEN cr.compteNumero IS NOT NULL THEN 'courant' ELSE 'epargne' END AS ACCOUNT_TYPE, " +
                "    cr.decouvert AS DECOUVERT, " +
                "    ep.tauxInteret AS TAUXINTERET " +
                "FROM compte c " +
                "LEFT JOIN courant cr ON c.numero = cr.compteNumero " +
                "LEFT JOIN epargne ep ON c.numero = ep.compteNumero " +
                "where c.etat = ?::etat_enum; ";
        List<Compte> comptes = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(Query);

            stmt.setString(1,status);
            ResultSet result = stmt.executeQuery();
            while (result.next()){
                if (result.getString("ACCOUNT_TYPE").equals("courant")){
                    Courant courant = new Courant();
                    courant.setNumero(result.getLong("numero"));
                    courant.setDecouvert(result.getDouble("decouvert"));

                    String etatValue = result.getString("etat");
                    Etat_enum etat = Etat_enum.valueOf(etatValue);
                    courant.setEtat(etat);
                    courant.setSolde(result.getDouble("solde"));
                    courant.setDateCreation(result.getDate("datecreation").toLocalDate());
                    Employer emp = new Employer();
                    emp.setMatricule(result.getInt("employermatricule"));
                    Client clt = new Client();
                    clt.setCode(result.getInt("clientcode"));
                    courant.setClient(clt);
                    courant.setEmplyer(emp);
                    comptes.add(courant);

                }else{
                    Epargne epargne = new Epargne();
                    epargne.setNumero(result.getLong("numero"));
                    epargne.setTauxInteret(result.getDouble("tauxinteret"));
                    String etatValue = result.getString("etat");
                    Etat_enum etat = Etat_enum.valueOf(etatValue);
                    epargne.setEtat(etat);
                    epargne.setSolde(result.getDouble("solde"));
                    epargne.setDateCreation(result.getDate("datecreation").toLocalDate());
                    Employer emp = new Employer();
                    emp.setMatricule(result.getInt("employermatricule"));
                    Client clt = new Client();
                    clt.setCode(result.getInt("clientcode"));
                    epargne.setClient(clt);
                    epargne.setEmplyer(emp);
                    comptes.add(epargne);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return comptes;
    }
    public  List<Compte> findByDateCreation(LocalDate Date){
        List<Compte> comptes = new ArrayList<>();
        String Query = "SELECT " +
                "    c.*, " +
                "    CASE WHEN cr.compteNumero IS NOT NULL THEN 'courant' ELSE 'epargne' END AS ACCOUNT_TYPE, " +
                "    cr.decouvert AS DECOUVERT, " +
                "    ep.tauxInteret AS TAUXINTERET " +
                "FROM compte c " +
                "LEFT JOIN courant cr ON c.numero = cr.compteNumero " +
                "LEFT JOIN epargne ep ON c.numero = ep.compteNumero " +
                "where c.datecreation >= ?; ";
        try {
            PreparedStatement stmt = connection.prepareStatement(Query);
            java.sql.Date creationDate = java.sql.Date.valueOf(Date);
            stmt.setDate(1,creationDate);
            ResultSet result = stmt.executeQuery();
            while (result.next()){
                if (result.getString("ACCOUNT_TYPE").equals("courant")){
                    Courant courant = new Courant();
                    courant.setNumero(result.getLong("numero"));
                    courant.setDecouvert(result.getDouble("decouvert"));

                    String etatValue = result.getString("etat");
                    Etat_enum etat = Etat_enum.valueOf(etatValue);
                    courant.setEtat(etat);
                    courant.setSolde(result.getDouble("solde"));
                    courant.setDateCreation(result.getDate("datecreation").toLocalDate());
                    Employer emp = new Employer();
                    emp.setMatricule(result.getInt("employermatricule"));
                    Client clt = new Client();
                    clt.setCode(result.getInt("clientcode"));
                    courant.setClient(clt);
                    courant.setEmplyer(emp);
                    comptes.add(courant);

                }else{
                    Epargne epargne = new Epargne();
                    epargne.setNumero(result.getLong("numero"));
                    epargne.setTauxInteret(result.getDouble("tauxinteret"));
                    String etatValue = result.getString("etat");
                    Etat_enum etat = Etat_enum.valueOf(etatValue);
                    epargne.setEtat(etat);
                    epargne.setSolde(result.getDouble("solde"));
                    epargne.setDateCreation(result.getDate("datecreation").toLocalDate());
                    Employer emp = new Employer();
                    emp.setMatricule(result.getInt("employermatricule"));
                    Client clt = new Client();
                    clt.setCode(result.getInt("clientcode"));
                    epargne.setClient(clt);
                    epargne.setEmplyer(emp);
                    comptes.add(epargne);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return comptes;
    }
}
