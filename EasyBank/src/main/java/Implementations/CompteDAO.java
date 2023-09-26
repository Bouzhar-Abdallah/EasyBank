package Implementations;

import Enums.Etat_enum;
import Objects.*;
import Services.CompteDAOInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompteDAO implements CompteDAOInterface {
    protected Connection connection;

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
        if (type.equals("courant")){
            try{
                PreparedStatement stmt = connection.prepareStatement(getAllQuery);
                ResultSet result = stmt.executeQuery();
                while(result.next()){
                    Courant compte = new Courant();
                    compte.setNumero(result.getLong("numero"));
                    compte.setSolde(result.getDouble("solde"));
                    compte.setDateCreation(result.getDate("datecreation").toLocalDate());
                    compte.setDecouvert(result.getDouble("decouvert"));
                    Client client = new Client();
                    client.setCode(result.getInt("clientcode"));
                    compte.setClient(client);
                    Employer employer = new Employer();
                    employer.setMatricule(result.getInt("employermatricule"));
                    compte.setEmplyer(employer);
                    String etatValue = result.getString("etat");
                    Etat_enum etat = Etat_enum.valueOf(etatValue);
                    compte.setEtat(etat);
                    comptes.add(compte);
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }else{
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
                    client.setCode(result.getInt("clientcode"));
                    compte.setClient(client);

                    Employer employer = new Employer();
                    employer.setMatricule(result.getInt("employermatricule"));
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
}
