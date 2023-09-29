package Services;

import Objects.Mission;

import java.util.Optional;

public interface MissionDAOInterface {
    Optional<Mission> create(Mission mission);
    Integer delete(Integer code);
}
