package newService;

import Objects.Agence;
import newImplementation.AgenceImp;

import java.util.Optional;

public class AgenceService {
    AgenceImp agenceImp;

    public AgenceService(AgenceImp agenceImp) {
        this.agenceImp = agenceImp;
    }
    public Optional<Agence> create(Agence agence){
        return agenceImp.create(agence);
    }
}
