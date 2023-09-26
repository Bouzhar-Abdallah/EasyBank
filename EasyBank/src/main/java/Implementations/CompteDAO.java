package Implementations;

import Objects.Compte;
import Services.CompteDAOInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
        return null;
    }

}
