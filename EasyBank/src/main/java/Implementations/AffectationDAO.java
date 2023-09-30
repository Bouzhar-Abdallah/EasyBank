package Implementations;

import Objects.Affectation;
import Services.AffectationDAOInterface;
import Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class AffectationDAO implements AffectationDAOInterface {
    protected Connection connection;
    public AffectationDAO() {
        connection = DBConnection.getDBConnection();
    }
    @Override
    public Optional<Affectation> create(Affectation affectation) {
        String query = "INSERT INTO affectation(datedebut,missionid,employerid) VALUES(?,?,?);";
        try{
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setDate(1,java.sql.Date.valueOf(affectation.getDateDebut()));
            stmt.setInt(2,affectation.getMission().getCode());
            stmt.setInt(3,affectation.getEmployer().getMatricule());
            if (stmt.executeUpdate() >0){
                return Optional.of(affectation);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Integer delete(Integer employerID, Integer missionId) {
        return null;
    }
}
