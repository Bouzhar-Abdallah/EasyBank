package newImplementation;

import interfaces.InterfaceData;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

public class DataImplimentation<Entity, Identifier> implements InterfaceData<Entity, Identifier> {
    @Override
    public Optional create(Entity entity) {
        return Optional.empty();
    }

    @Override
    public Optional update(Entity entity) {
        return Optional.empty();
    }

    @Override
    public Optional findByID(Identifier id, Class<Entity> entityClass) {
        return Optional.empty();
    }

    @Override
    public List getAll(Class<Entity> entityClass) {
        return null;
    }

    @Override
    public boolean delete(Identifier id, Class<Entity> entityClass) {
        return false;
    }
}
