package newService;

import Objects.Affectation;
import newImplementation.AffectationImp;

import java.util.Optional;

public class AffectationService {
    AffectationImp affectationImp;

    public AffectationService(AffectationImp affectationImp) {
        this.affectationImp = affectationImp;
    }
    public Optional<Affectation> create(Affectation affectation){return affectationImp.create(affectation);}
}
