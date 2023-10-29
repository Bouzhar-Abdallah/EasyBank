package utils;

public class DatabaseField<T> {
    public DatabaseField(){}
    private String fieldName;
    private Class<T> fieldType;
    private T value;

    public DatabaseField(String fieldName, Class<T> fieldType, T value) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.value = value;
    }

    @Override
    public String toString() {
        return "DatabaseField{" +
                "fieldName='" + fieldName + '\'' +
                ", fieldType=" + fieldType +
                ", value=" + value +
                '}';
    }

    public String getFieldName() {
        return fieldName;
    }

    public Class<T> getFieldType() {
        return fieldType;
    }

    public T getValue() {
        return value;
    }
}
