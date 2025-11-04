package org.l0gik67;

import jakarta.persistence.Column;
import jakarta.persistence.Table;

import org.junit.jupiter.api.Test;
import org.l0gik67.entity.User;

import java.lang.reflect.Field;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;



class HibernateRunnerTest {
    @Test
    public void testHibernateApi() throws SQLException, IllegalAccessException {
        var user = User.builder()
                .username("ivanov@")
                .firstname("ivan")
                .lastname("ivanov")
                .birthDate(LocalDate.of(2000, 01, 01))
                .age(23)
                .build();
        var sql = """
                  insert into
                  %s
                  (%s)
                  values
                  (%s)
                  """;

        var tableName = Optional.ofNullable(user.getClass().getAnnotation(Table.class))
                .map(table -> table.schema() + "." + table.name())
                .orElse(user.getClass().getName());

        Field[] fields = user.getClass().getDeclaredFields();

        var columnNames = Arrays.stream(fields)
                .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName())
                ).collect(Collectors.joining(", "));
        var columnValues = Arrays.stream(fields)
                .map(field -> "?")
                .collect(Collectors.joining(", "));

        var sql_prepare_statement = sql.formatted(tableName,
                                                    columnNames,
                                                    columnValues);

        var connection = DriverManager.getConnection("jdbc:postgresql://localhost:15200/postgres",
                "postgres", "strong_p@ssword123");
        var statement = connection.prepareStatement(sql_prepare_statement);
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            statement.setObject(i + 1, fields[i].get(user));
        }

        statement.executeUpdate();

        statement.close();
        connection.close();
    }
}