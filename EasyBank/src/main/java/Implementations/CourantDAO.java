package Implementations;

import Enums.Etat_enum;
import Objects.*;
import Services.CourantDAOInterface;
import Utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public class CourantDAO extends CompteDAO implements CourantDAOInterface {
    public CourantDAO(){connection = DBConnection.getDBConnection();}
    @Override
    public Optional<Compte> create(Compte compte) {
        Courant epargneCompte = (Courant) compte;
        try {
            connection.setAutoCommit(false);
            String insertQuery = "INSERT INTO compte(numero, datecreation, etat, employermatricule, clientcode) VALUES (NEXTVAL('account_number_seq'), CURRENT_TIMESTAMP, ?::etat_enum, ?, ?) RETURNING numero;";
            PreparedStatement stmt = connection.prepareStatement(insertQuery);
            stmt.setString(1, Etat_enum.actif.name());
            stmt.setInt(2, compte.getEmplyer().getMatricule());
            stmt.setInt(3, compte.getClient().getCode());
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                long accountNumber = result.getLong("numero");

                String insertEpargneQuery = "INSERT INTO courant(compteNumero, decouvert) VALUES (?,?);";
                PreparedStatement stmtEpargne = connection.prepareStatement(insertEpargneQuery);
                stmtEpargne.setLong(1, accountNumber);
                stmtEpargne.setDouble(2, ((Courant) compte).getDecouvert());

                int affectedRows = stmtEpargne.executeUpdate();

                if (affectedRows != 1) {
                    connection.rollback();
                    return Optional.empty();
                }

                connection.commit();
                return searchByNumero(accountNumber);
            } else {
                connection.rollback();
                return Optional.empty();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Compte> update(Compte compte) {
        return Optional.empty();
    }
    public Optional<Compte> searchByNumero(long numero){
        EmployerDAO employerDAO = new EmployerDAO();
        ClientDAO clientDAO = new ClientDAO();
        Courant courant = new Courant();
        String searchQuery = "SELECT " +
                "  compte.numero, " +
                "  compte.solde, " +
                "  compte.datecreation, " +
                "  compte.employermatricule, " +
                "  compte.clientcode, " +
                "  compte.etat, " +
                "  courant.decouvert, " +
                "  courant.comptenumero " +
                "FROM " +
                "  compte " +
                "   INNER JOIN courant " +
                "   ON compte.numero = courant.comptenumero " +
                "WHERE" +
                "    compte.numero = ?" +
                ";";
        try{
            PreparedStatement stmt = connection.prepareStatement(searchQuery);
            stmt.setLong(1,numero);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                courant.setNumero(resultSet.getLong("numero"));
                courant.setDecouvert(resultSet.getDouble("decouvert"));
                /* not complet */
                if (clientDAO.searchByClientCode(resultSet.getInt("clientcode")).isPresent()){
                    courant.setClient((Client) clientDAO.searchByClientCode(resultSet.getInt("clientcode")).get());
                }
                if(employerDAO.searchByMatricule(resultSet.getInt("employermatricule")).isPresent()){
                    courant.setEmplyer((Employer) employerDAO.searchByMatricule(resultSet.getInt("employermatricule")).get());
                }
                return Optional.of(courant);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }
    public List<Compte> getAll(){
        return getAll("courant");
    }
}
