package newService;

import Objects.Mission;
import newImplementation.MissionImp;

import java.util.Optional;

public class MissionService {
    MissionImp missionImp;

    public MissionService(MissionImp missionImp) {
        this.missionImp = missionImp;
    }
    public Optional<Mission> create(Mission mission){return missionImp.create(mission);}
}
