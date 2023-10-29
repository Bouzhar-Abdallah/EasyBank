package newImplementation;

import interfaces.InterfaceData;

import javax.swing.text.html.parser.Entity;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DataImplimentation<Entity, Identifier> implements InterfaceData<Entity, Identifier> {
    protected Connection connection;
    @Override
    public Optional<Entity> create(Entity entity) {
        System.out.println("from Data Implimentation");
        System.out.println(entity.getClass().getSimpleName());
        /**/
        Class<?> entityClass = entity.getClass();
        String tableName = entityClass.getSimpleName(); // Use class name as the table name

        StringBuilder columns = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();

        List<Field> allFields = new ArrayList<>();

// Iterate through the class hierarchy to get all declared fields
        Class<?> currentClass = entityClass;
        while (currentClass != null) {
            Field[] declaredFields = currentClass.getDeclaredFields();
            allFields.addAll(Arrays.asList(declaredFields));
            currentClass = currentClass.getSuperclass();
        }

        for (Field field : allFields) {
            // Check if the field is a list type (or any other type you want to exclude)
            if (field.getType().isAssignableFrom(List.class)) {
                continue; // Skip this field
            }

            // You might want to add some logic here to handle different field types.
            columns.append(field.getName()).append(",");
            placeholders.append("?,");
        }

// Remove the trailing comma from columns and placeholders
        columns.setLength(columns.length() - 1);
        placeholders.setLength(placeholders.length() - 1);

        String insertSql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";
        System.out.println(insertSql);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            int parameterIndex = 1;
            for (Field field : allFields) {
                // Check if the field is a list type (or any other type you want to exclude)
                if (field.getType().isAssignableFrom(List.class)) {
                    continue; // Skip this field
                }

                field.setAccessible(true);
                Object value = field.get(entity);
                preparedStatement.setObject(parameterIndex, value);
                parameterIndex++;
            }

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return Optional.of(entity);
            }
        } catch (SQLException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }




        /**/
        return Optional.empty();
    }

    @Override
    public Optional<Entity> update(Entity entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Entity> findByID(Identifier id, Class<Entity> entityClass) {
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
