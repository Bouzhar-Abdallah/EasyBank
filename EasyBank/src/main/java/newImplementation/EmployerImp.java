package newImplementation;

import Objects.Employer;

import java.util.List;
import java.util.Optional;

public class EmployerImp extends DataImplimentation<Employer,String>{
    public Optional create(Employer employer) {
        return super.create(employer);
    }

    public Optional update(Employer employer) {
        return super.update(employer);
    }

    public Optional findByID(String id, Class<Employer> employerClass) {
        return super.findByID(id, employerClass);
    }

    public List getAll(Class<Employer> employerClass) {
        return super.getAll(employerClass);
    }

    public boolean delete(String id, Class<Employer> employerClass) {
        return super.delete(id, employerClass);
    }
}
