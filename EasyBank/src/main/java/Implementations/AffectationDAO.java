package Implementations;

import Objects.Affectation;
import Objects.Employer;
import Objects.Mission;
import Services.AffectationDAOInterface;
import Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public List<Affectation> getEmployerAffectations(Employer employer){
        MissionDAO missionDAO = new MissionDAO();
        String query = "select * from affectation where employerId = ? ORDER BY dateDebut DESC";
        List<Affectation> affectations = new ArrayList<>();
        try{
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1,employer.getMatricule());
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()){
                Affectation aff = new Affectation();
                aff.setEmployer(employer);
                java.sql.Date dateDebutSql = resultSet.getDate("dateDebut");
                if (dateDebutSql != null) {
                    aff.setDateDebut(dateDebutSql.toLocalDate());
                }
                java.sql.Date dateFinSql = resultSet.getDate("dateFin");
                if (dateFinSql != null) {
                    aff.setDateFin(dateFinSql.toLocalDate());
                }
                Optional<Mission> optMission = missionDAO.getMissionById(resultSet.getInt("missionId"));
                if (optMission.isPresent()){
                    aff.setMission(optMission.get());
                }
                affectations.add(aff);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    return affectations;
    }
    public Integer delete(Affectation affectation){
        String query = "delete from affectation where employerId = ? AND missionId = ? AND dateDebut = ? ";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, affectation.getEmployer().getMatricule());
            stmt.setInt(2, affectation.getMission().getCode());
            stmt.setDate(3, java.sql.Date.valueOf(affectation.getDateDebut()));

            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
