package Implementations;

import Objects.Operation;
import Services.OperationsInterface;
import Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Properties;

public class OperationDAO implements OperationsInterface {
    protected Connection connection;

    public OperationDAO() {
        connection = DBConnection.getDBConnection();
    }

    @Override
    public Integer delete(long numero) {
        return null;
    }

    @Override
    public Optional<Operation> create(Operation operation) {
        String query = "INSERT INTO operation(numero,type,employercode,comptenumero,montant,dateoperation) VALUES(?,?::type_operation_enum,?,?,?,CURRENT_TIMESTAMP);";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1,9854352394342134L);
            stmt.setString(2,operation.getType().name());
            stmt.setInt(3,operation.getEmployer().getMatricule());
            stmt.setLong(4,operation.getCompte().getNumero());
            stmt.setFloat(5,operation.getMontant());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Operation> findByNumero(long numero) {
        return Optional.empty();
    }
}
