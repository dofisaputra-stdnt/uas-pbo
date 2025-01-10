package edu.umb.uaspbo.util;

import java.lang.reflect.Field;
import java.sql.ResultSet;

public class EntityMapper {

    public static <T> T mapToEntity(ResultSet resultSet, Class<T> entityType) {
        try {
            T entity = entityType.getDeclaredConstructor().newInstance();
            for (Field field : entityType.getDeclaredFields()) {
                field.setAccessible(true);
                String columnName = camelToSnakeCase(field.getName());
                Object value = resultSet.getObject(columnName);
                field.set(entity, value);
            }
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping ResultSet to entity", e);
        }
    }

    private static String camelToSnakeCase(String str) {
        return str.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

}
