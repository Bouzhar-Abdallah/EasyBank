package Implementations;

import Enums.Etat_enum;
import Enums.Type_operation_enum;
import Objects.Compte;
import Objects.Employer;
import Objects.Operation;
import Objects.Person;
import Services.OperationsInterface;
import Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Properties;

public class OperationDAO implements OperationsInterface {
    protected Connection connection;
    private CompteDAO compteDAO;
    private EmployerDAO employerDAO;
    public OperationDAO() {
        connection = DBConnection.getDBConnection();
        compteDAO = new CompteDAO();
        employerDAO = new EmployerDAO();
    }

    @Override
    public Integer delete(long numero) {
        return null;
    }

    @Override
    public Optional<Operation> create(Operation operation) {
        String query = "INSERT INTO operation(numero,type,employercode,comptenumero,montant,dateoperation) VALUES(NEXTVAL('operation_number_seq'),?::type_operation_enum,?,?,?,CURRENT_TIMESTAMP);";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1,operation.getType().name());
            stmt.setInt(2,operation.getEmployer().getMatricule());
            stmt.setLong(3,operation.getCompte().getNumero());
            stmt.setFloat(4,operation.getMontant());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Operation> findByNumero(long numero) {
        String query = "SELECT * FROM operation where numero = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1,numero);
            ResultSet result = stmt.executeQuery();
            if (result.next()){
                Operation operation = new Operation();
                operation.setMontant(result.getFloat("montant"));
                String etatValue = result.getString("type");
                Type_operation_enum type = Type_operation_enum.valueOf(etatValue);
                operation.setType(type);
                operation.setNumero(result.getLong("numero"));
                operation.setDateOperation(result.getDate("dateOperation").toLocalDate());
                Optional<Compte> compte = compteDAO.findByNumero(result.getLong("compteNumero"));
                if (compte.isPresent()){
                    operation.setCompte(compte.get());
                }
                Optional<Person> employer = employerDAO.searchByMatricule(result.getInt("employerCode"));
                if (employer.isPresent()){
                    operation.setEmployer((Employer) employer.get());
                }
                return Optional.of(operation);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }
    public int delete(Operation operation){
        String query = "DELETE from operation where numero = ?;";
        try{
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1,operation.getNumero());
            return stmt.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
