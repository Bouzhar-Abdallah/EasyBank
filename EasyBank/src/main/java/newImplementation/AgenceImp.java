package newImplementation;

import Objects.Agence;
import utils.DBConnection;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class AgenceImp extends DataImplimentation<Agence,Integer>{

    public AgenceImp() {
        connection = DBConnection.getDBConnection();
    }
    public Optional<Agence> create(Agence agence) {
        System.out.println("\nfrom agence Implimentation");
        System.out.println(agence.getClass().getSimpleName());
        return super.create(agence);
    }



    public Optional<Agence> findByID(Integer id, Class<Agence> agenceClass) {
        return super.findByID(id, agenceClass);
    }

    public List getAll(Class<Agence> agenceClass) {
        return super.getAll(agenceClass);
    }

    public boolean delete(Integer id, Class<Agence> agenceClass) {
        return super.delete(id, agenceClass);
    }
}
