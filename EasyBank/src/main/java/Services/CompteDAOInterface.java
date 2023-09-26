package Services;

import Objects.Compte;

import java.util.List;

public interface CompteDAOInterface {
Integer delete(Long numero);
List<Compte> getAll(String type);
}
