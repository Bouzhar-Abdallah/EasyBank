package Implementations;

import Enums.Etat_enum;
import Objects.*;
import Services.EpargneDAOInterface;
import Utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EpargneDAO extends CompteDAO implements EpargneDAOInterface {
    public EpargneDAO() {
        connection = DBConnection.getDBConnection();
    }

    @Override
    public Optional<Compte> create(Compte compte) {
        Epargne epargneCompte = (Epargne) compte;
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

                String insertEpargneQuery = "INSERT INTO epargne(compteNumero, tauxinteret) VALUES (?, ?);";
                PreparedStatement stmtEpargne = connection.prepareStatement(insertEpargneQuery);
                stmtEpargne.setLong(1, accountNumber);
                stmtEpargne.setDouble(2, ((Epargne) compte).getTauxInteret());
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
        Epargne epargne = new Epargne();
        String searchQuery = "SELECT " +
                "  compte.numero, " +
                "  compte.solde, " +
                "  compte.datecreation, " +
                "  compte.employermatricule, " +
                "  compte.clientcode, " +
                "  epargne.comptenumero, " +
                "  epargne.tauxinteret " +
                "FROM " +
                "  compte " +
                "   INNER JOIN epargne " +
                "   ON compte.numero = epargne.comptenumero " +
                "WHERE" +
                "    compte.numero = ?" +
                ";";
        try{
            PreparedStatement stmt = connection.prepareStatement(searchQuery);
            stmt.setLong(1,numero);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                epargne.setNumero(resultSet.getLong("numero"));
                epargne.setTauxInteret(resultSet.getDouble("tauxinteret"));
                /* not complet */
                if (clientDAO.searchByClientCode(resultSet.getInt("clientcode")).isPresent()){
                    epargne.setClient((Client) clientDAO.searchByClientCode(resultSet.getInt("clientcode")).get());
                }
                if(employerDAO.searchByMatricule(resultSet.getInt("employermatricule")).isPresent()){
                    epargne.setEmplyer((Employer) employerDAO.searchByMatricule(resultSet.getInt("employermatricule")).get());
                }
                return Optional.of(epargne);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }
    public List<Compte> getAll(){
        return getAll("epargne");
    }
}
