package newImplementation;

import Objects.Mission;
import utils.DBConnection;

public class MissionImp extends DataImplimentation<Mission,Integer>{
    public MissionImp() {
        connection = DBConnection.getDBConnection();
    }
}
