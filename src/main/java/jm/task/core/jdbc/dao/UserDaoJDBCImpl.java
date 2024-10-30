package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        boolean result = checkTable();

        if (!result) {
            String query =  "CREATE TABLE users " +
                    "(id INTEGER PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), last_name VARCHAR(50), age INTEGER)";
            executeQuery(query);
        }
    }

    public void dropUsersTable() {
        boolean result = checkTable();

        if (result) {
            String query =  "DROP TABLE users";
            executeQuery(query);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query =  "INSERT INTO users(name, last_name, age) VALUES ('" +
                        name + "', '" + lastName + "', '" + age + "')";
        executeQuery(query);
    }

    public void removeUserById(long id) {
        String query =  "DELETE FROM users WHERE id = " + id;
        executeQuery(query);
    }

    public List<User> getAllUsers() {
        String query =  "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                byte age = resultSet.getByte("age");

                users.add(new User(name, lastName, age));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    public void cleanUsersTable() {
        String query =  "DELETE FROM users";

        executeQuery(query);
    }

    private boolean executeQuery(String query) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private boolean checkTable() {
        int tableCount = 0;

        String query =  "SELECT EXISTS(SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'kata_academy' " +
                        "AND TABLE_NAME = 'users') AS table_exists";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            resultSet.next();
            tableCount = resultSet.getInt("table_exists");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tableCount == 1;
    }
}
