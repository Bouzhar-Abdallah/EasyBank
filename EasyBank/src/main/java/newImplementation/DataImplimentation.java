package newImplementation;

import annotations.*;
import interfaces.InterfaceData;
import utils.DatabaseField;

import javax.swing.text.html.parser.Entity;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DataImplimentation<Entity, Identifier> implements InterfaceData<Entity, Identifier> {
    protected Connection connection;

    @Override
    public Optional<Entity> create(Entity entity) {
        List<DatabaseField<Entity>> fields = generateDatabaseFields(entity);
/*        for (DatabaseField<Entity> field: fields
             ) {
            System.out.println(field.toString());
        }*/
        String tableName = entity.getClass().getSimpleName();
        String insertSql = generateInsertQuery(tableName, fields);

        try {
            PreparedStatement preparedStatement = prepareStatement(connection, insertSql, fields);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return Optional.of(entity);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    private List<DatabaseField<Entity>> generateDatabaseFields(Entity entity) {
        List<DatabaseField<Entity>> fields = new ArrayList<>();
        Class<?> entityClass = entity.getClass();

        while (entityClass != null) {
            Field[] entityFields = entityClass.getDeclaredFields();

            for (Field field : entityFields) {
                field.setAccessible(true);
                Object value;
                try {
                    value = field.get(entity);
                } catch (IllegalAccessException e) {
                    value = null; // Handle appropriately if you can't access the value
                }

                Class<?> fieldType = field.getType();
                String fieldName = field.getName();

                if (field.isAnnotationPresent(CustomField.class)) {
                    // Handle fields with @CustomField annotation
                    fields.add(new DatabaseField<>(fieldName, (Class<Entity>) fieldType, (Entity) value));
                } else if (field.isAnnotationPresent(ManyToOne.class)) {
                    // Handle fields with @ManyToOne annotation
                    if (value != null) {
                        // Extract the related object's property and set it as the value
                        DatabaseField<Entity> relatedFieldInfo = extractRelatedFieldInfo(value);
                        if (relatedFieldInfo != null) {
                            fields.add(relatedFieldInfo);
                        }
                    }
                }
            }

            // Move to the superclass for the next iteration
            entityClass = entityClass.getSuperclass();
        }

        return fields;
    }

    // Extract the related object's property information
    @SuppressWarnings("unchecked")
    private DatabaseField<Entity> extractRelatedFieldInfo(Object relatedObject) {
        Class<?> relatedClass = relatedObject.getClass();
        Field[] relatedFields = relatedClass.getDeclaredFields();

        for (Field relatedField : relatedFields) {
            if (relatedField.isAnnotationPresent(Id.class)) {
                relatedField.setAccessible(true);
                Object relatedValue;
                try {
                    relatedValue = relatedField.get(relatedObject);
                    String fieldName =relatedObject.getClass().getSimpleName().concat(relatedField.getName());

                    Class<?> fieldType = relatedField.getType();
                    return new DatabaseField<>(fieldName, (Class<Entity>) fieldType, (Entity) relatedValue);
                } catch (IllegalAccessException e) {
                    // Handle exceptions if necessary
                }
            }
        }
        return null; // Return null if no @Id annotation is found
    }

    private String generateInsertQuery(String tableName, List<DatabaseField<Entity>> fields) {
        StringBuilder columns = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();

        for (DatabaseField<Entity> field : fields) {
            if (field.getFieldType().isEnum()){
            columns.append(field.getFieldName()).append(",");
            placeholders.append("?::").append(field.getFieldType().getSimpleName()).append(",");
            }else{
                columns.append(field.getFieldName()).append(",");
                placeholders.append("?,");
            }
        }

        columns.setLength(columns.length() - 1);
        placeholders.setLength(placeholders.length() - 1);
        System.out.println("query: INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")" );
        return "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";
    }
    private PreparedStatement prepareStatement(Connection connection, String query, List<DatabaseField<Entity>> fields) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        int parameterIndex = 1;

        for (DatabaseField<Entity> field : fields) {
            Object value = field.getValue();

            if (value instanceof Enum<?>) {
                preparedStatement.setObject(parameterIndex, value.toString());
            } else {
                preparedStatement.setObject(parameterIndex, value);
            }

            parameterIndex++;
        }

        return preparedStatement;
    }

    // Custom logic to process OneToMany relationship fields
    private void processOneToManyRelationship(Field field, Object value) {
        // Implement your logic to process the OneToMany relationship field value.
        // For example, set the foreign key value(s) based on the relationship value.
        // Be sure to handle exceptions and process the relationship correctly.

    }

    // Custom logic to process ManyToMany relationship fields
    private void processManyToManyRelationship(Field field, Object value) {
        // Implement your logic to process the ManyToMany relationship field value.
        // Be sure to handle exceptions and process the relationship correctly.

    }

    // Custom logic to process normal fields that are not relationships

    /*
    @SuppressWarnings("unchecked")
    before annotations
    private List<DatabaseField<Entity>> generateDatabaseFields(Entity entity) {
        List<DatabaseField<Entity>> fields = new ArrayList<>();
        Class<?> entityClass = entity.getClass();

        while (entityClass != null) {
            Field[] entityFields = entityClass.getDeclaredFields();

            for (Field field : entityFields) {
                field.setAccessible(true);
                Object value;
                try {
                    value = field.get(entity);
                } catch (IllegalAccessException e) {
                    value = null; // Handle appropriately if you can't access the value
                }

                Class<?> fieldType = field.getType();

                fields.add(new DatabaseField<>(field.getName(), (Class<Entity>) fieldType, (Entity) value));
            }

            // Move to the superclass for the next iteration
            entityClass = entityClass.getSuperclass();
        }

        return fields;
    }
    */


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
