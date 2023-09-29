package Implementations;

import Objects.Mission;
import Services.MissionDAOInterface;
import Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MissionDAO implements MissionDAOInterface {
    protected Connection connection;
    public MissionDAO() {
        connection = DBConnection.getDBConnection();
    }
    @Override
    public Optional<Mission> create(Mission mission) {
        String query = "insert into mission(nom,description) values(?,?) RETURNING code";
        try{
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1,mission.getNom());
            stmt.setString(2,mission.getDescription());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                mission.setCode(resultSet.getInt("code"));
                return Optional.of(mission);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Integer delete(Mission mission) {
        return null;
    }
}
