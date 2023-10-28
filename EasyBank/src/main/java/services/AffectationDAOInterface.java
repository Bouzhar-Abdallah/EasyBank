package Services;

import Objects.Affectation;

import java.util.Optional;

public interface AffectationDAOInterface {
    Optional<Affectation> create(Affectation affectation);
    Integer delete(Integer employerID,Integer missionId);
}
