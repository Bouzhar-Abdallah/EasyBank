package newImplementation;

import Objects.Employer;
import utils.DBConnection;

import java.util.List;
import java.util.Optional;

public class EmployerImp extends DataImplimentation<Employer,String>{
    public EmployerImp() {
        connection = DBConnection.getDBConnection();
    }

    public Optional<Employer> update(Employer employer) {
        return super.update(employer);
    }

    public Optional<Employer> findByID(String id, Class<Employer> employerClass) {
        return super.findByID(id, employerClass);
    }

    public List<Employer> getAll(Class<Employer> employerClass) {
        return super.getAll(employerClass);
    }

    public boolean delete(String id, Class<Employer> employerClass) {
        return super.delete(id, employerClass);
    }
}
