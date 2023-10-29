package newService;

import Objects.Courant;
import Objects.Epargne;
import newImplementation.CourantImp;
import newImplementation.EpargneImp;

import java.util.Optional;

public class CourantService {
    CourantImp courantImp;

    public CourantService(CourantImp courantImp) {
        this.courantImp = courantImp;
    }
    public Optional<Courant> create(Courant courant){
        return courantImp.create(courant);
    }
}
