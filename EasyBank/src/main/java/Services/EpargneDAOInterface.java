package Services;

import Objects.Compte;

import java.util.Optional;

public interface EpargneDAOInterface {
    Optional<Compte> create(Compte compte);
    Optional<Compte> update(Compte compte);

}
