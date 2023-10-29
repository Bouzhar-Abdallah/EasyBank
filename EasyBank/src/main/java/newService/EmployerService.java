package newService;

import Objects.Employer;
import newImplementation.AgenceImp;
import newImplementation.EmployerImp;

import java.util.Optional;

public class EmployerService {
    EmployerImp employerImp;

    public EmployerService(EmployerImp employerImp) {
        this.employerImp = employerImp;
    }
    public Optional<Employer> create(Employer employer){
        return employerImp.create(employer);
    }
}
