package ru.otus.jdbc.mapper;

import ru.otus.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T>{

    private final List<Field> withoutIdFields = new ArrayList<>();
    private final Constructor constructor;
    private Field idField;
    private final String name;

    public EntityClassMetaDataImpl(Class<T> entityClass) {
        Objects.requireNonNull(entityClass);
        name = entityClass.getSimpleName();
        try {
            constructor = entityClass.getConstructor();
            Arrays.stream(entityClass.getDeclaredFields())
                  .forEach(field ->  {
                      if (field.isAnnotationPresent(Id.class)) {
                          idField = field;
                      } else {
                          withoutIdFields.add(field);
                      }
                  });
            Optional.ofNullable(idField).orElseThrow(() -> new Exception("Cannot resolve id field"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Constructor getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        List<Field> allFields = new ArrayList<>(withoutIdFields.size() + 1);
        Collections.copy(allFields, withoutIdFields);
        allFields.add(idField);
        return Collections.unmodifiableList(allFields);
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return Collections.unmodifiableList(withoutIdFields);
    }

}
