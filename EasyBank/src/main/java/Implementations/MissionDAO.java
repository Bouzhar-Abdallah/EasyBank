package Implementations;

import Objects.Mission;
import Services.MissionDAOInterface;
import Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MissionDAO implements MissionDAOInterface {
    protected Connection connection;

    public MissionDAO() {
        connection = DBConnection.getDBConnection();
    }

    @Override
    public Optional<Mission> create(Mission mission) {
        String query = "insert into mission(nom,description) values(?,?) RETURNING code";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, mission.getNom());
            stmt.setString(2, mission.getDescription());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                mission.setCode(resultSet.getInt("code"));
                return Optional.of(mission);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Integer delete(Integer code) {
        String query = "delete from mission where code = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, code);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public List<Mission> getAllMissions() {
        List<Mission> missions = new ArrayList<>();
        String query = "select * from mission";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                Mission mission = new Mission();
                mission.setCode(results.getInt("code"));
                mission.setNom(results.getString("nom"));
                mission.setDescription(results.getString("description"));
                missions.add(mission);
            }
            return missions;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return missions;
    }

    public Optional<Mission> getMissionById(int id) {
        String query = "select * from mission where code = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet results = stmt.executeQuery();
            Mission mission = new Mission();
            if (results.next()) {
                mission.setCode(results.getInt("code"));
                mission.setNom(results.getString("nom"));
                mission.setDescription(results.getString("description"));
            }
            return Optional.of(mission);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }
}
