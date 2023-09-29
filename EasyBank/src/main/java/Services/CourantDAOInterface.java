package Services;

import Objects.Compte;

import java.util.Optional;

public interface CourantDAOInterface {
    Optional<Compte> create(Compte compte);
    Optional<Compte> update(Compte compte);
}
