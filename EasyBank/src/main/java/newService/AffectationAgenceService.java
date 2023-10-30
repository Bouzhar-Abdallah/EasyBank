package newService;

import Objects.AffectationAgence;
import newImplementation.AffectationAgenceimp;

import java.util.Optional;

public class AffectationAgenceService {
    AffectationAgenceimp affectationAgenceimp;

    public AffectationAgenceService(AffectationAgenceimp affectationAgenceimp) {
        this.affectationAgenceimp = affectationAgenceimp;
    }
    public Optional<AffectationAgence> create(AffectationAgence affectationAgence){
        return affectationAgenceimp.create(affectationAgence);
    }
}
