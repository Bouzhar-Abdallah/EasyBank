package newImplementation;

import Objects.AffectationAgence;
import utils.DBConnection;

public class AffectationAgenceimp extends DataImplimentation<AffectationAgence,String>{
    public AffectationAgenceimp() {connection = DBConnection.getDBConnection();
    }
}
