package Services;

import Objects.Operation;

import java.util.Optional;

public interface OperationsInterface {
    Integer delete(long numero);
    Optional<Operation> create(Operation operation);
    Optional<Operation> findByNumero(long numero);
}
