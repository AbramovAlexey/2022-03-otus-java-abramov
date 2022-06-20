package ru.otus.jdbc.mapper;

import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData{

    private static final String DELIMITER = ",";
    private final EntityClassMetaData<T> entityClassMetaData;

    @Override
    public String getSelectAllSql() {
        return String.format("select %s from %s",
                             entityClassMetaData.getAllFields()
                                                .stream()
                                                .map(Field::getName)
                                                .collect(Collectors.joining(DELIMITER)),
                             entityClassMetaData.getName());
    }

    @Override
    public String getSelectByIdSql() {
        return String.format("%s where %s = ? ", getSelectAllSql(), entityClassMetaData.getIdField().getName());
    }

    @Override
    public String getInsertSql() {
        return String.format("insert into %s (%s) values (%s)",
                             entityClassMetaData.getName(),
                             entityClassMetaData.getFieldsWithoutId()
                                                .stream()
                                                .map(Field::getName)
                                                .collect(Collectors.joining(DELIMITER)),
                             IntStream.rangeClosed(1, entityClassMetaData.getFieldsWithoutId().size())
                                      .mapToObj(f -> "?")
                                      .collect(Collectors.joining(DELIMITER)));
    }

    @Override
    public String getUpdateSql() {
        return String.format("update %s set %s where %s = ?",
                             entityClassMetaData.getName(),
                             entityClassMetaData.getFieldsWithoutId()
                                                .stream()
                                                .map(field -> String.format("%s = ?", field.getName()))
                                                .collect(Collectors.joining(DELIMITER)),
                             entityClassMetaData.getIdField().getName());
    }

}
