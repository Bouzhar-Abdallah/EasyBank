package newImplementation;

import Objects.Courant;
import Objects.Epargne;
import utils.DBConnection;

public class CourantImp extends DataImplimentation<Courant,Long>{
    public CourantImp() {
        connection = DBConnection.getDBConnection();
    }
}
