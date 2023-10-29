package newImplementation;

import Objects.Epargne;
import utils.DBConnection;

public class EpargneImp extends DataImplimentation<Epargne,Long>{
    public EpargneImp() {
        connection = DBConnection.getDBConnection();
    }
}
