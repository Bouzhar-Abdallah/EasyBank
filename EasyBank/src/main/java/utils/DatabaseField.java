package utils;

public class DatabaseField<T> {
    private String fieldName;
    private Class<T> fieldType;
    private T value;

    public DatabaseField(String fieldName, Class<T> fieldType, T value) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.value = value;
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

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setFieldType(Class<T> fieldType) {
        this.fieldType = fieldType;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
