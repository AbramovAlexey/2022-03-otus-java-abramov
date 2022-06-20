package ru.otus.jdbc.mapper;

import lombok.RequiredArgsConstructor;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
@RequiredArgsConstructor
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return  dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return getObject(rs);
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
           var list = new ArrayList<T>();
           try {
               while (rs.next()) {
                   list.add(getObject(rs));
               }
               return list;
           } catch (SQLException e) {
               throw new DataTemplateException(e);
           }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T object) {
        var parameters = entityClassMetaData.getFieldsWithoutId()
                .stream()
                .map(field -> {
                    field.setAccessible(true);
                    try {
                        return field.get(object);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Fail to get field value");
                    }
                })
                .collect(Collectors.toList());
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), parameters);
    }

    @Override
    public void update(Connection connection, T object) {
        var parameters = entityClassMetaData.getAllFields()
                                                        .stream()
                                                        .map(field -> {
                                                            field.setAccessible(true);
                                                            try {
                                                                return field.get(object);
                                                            } catch (IllegalAccessException e) {
                                                               throw new RuntimeException("Fail to get field value");
                                                            }
                                                        })
                                                        .collect(Collectors.toList());
        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), parameters);
    }

    private T getObject(ResultSet rs) throws SQLException {
        List<Object> args = new ArrayList<>();
        args.add(rs.getLong(entityClassMetaData.getIdField().getName()));
        for (var field : entityClassMetaData.getFieldsWithoutId()) {
            args.add(rs.getObject(field.getName()));
        }
        try {
            return entityClassMetaData.getConstructor().newInstance(args.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
