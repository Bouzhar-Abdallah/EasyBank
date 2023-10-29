package newService;

import Objects.Epargne;
import newImplementation.EpargneImp;

import java.util.Optional;

public class EpargneService {
    EpargneImp epargneImp;

    public EpargneService(EpargneImp epargneImp) {
        this.epargneImp = epargneImp;
    }
    public Optional<Epargne> create(Epargne epargne){
        return epargneImp.create(epargne);
    }
}
