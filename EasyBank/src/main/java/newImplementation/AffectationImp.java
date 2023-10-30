package newImplementation;

import Objects.Affectation;
import utils.DBConnection;

public class AffectationImp extends DataImplimentation<Affectation,String>{
    public AffectationImp() {connection = DBConnection.getDBConnection();
    }
}
