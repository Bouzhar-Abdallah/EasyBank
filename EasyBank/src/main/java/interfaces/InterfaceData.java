package interfaces;

import java.util.List;
import java.util.Optional;

public interface InterfaceData<Entity, Identifier> {
    public Optional<Entity> create(Entity entity);
    public Optional<Entity> update(Entity entity);
    public Optional<Entity> findByID(Identifier id,Class<Entity> entityClass);
    public List<Entity> getAll(Class<Entity> entityClass);
    public boolean delete(Identifier id,Class<Entity> entityClass);
}
