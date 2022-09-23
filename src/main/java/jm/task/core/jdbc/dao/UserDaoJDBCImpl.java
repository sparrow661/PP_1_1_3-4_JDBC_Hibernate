package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    Connection connection = null;
    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement createUsersTable = connection.createStatement()) {
            connection.setAutoCommit(false);
            createUsersTable.executeUpdate("CREATE TABLE Users(" +
                    "id INTEGER NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR(64) NOT NULL," +
                    "lastName VARCHAR(64) NOT NULL," +
                    "age SMALLINT NOT NULL," +
                    "PRIMARY KEY(ID))");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement dropUsersTable = connection.createStatement()) {
            connection.setAutoCommit(false);
            dropUsersTable.executeUpdate("DROP TABLE if exists Users ");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement saveUser = connection.prepareStatement("insert into Users values (default, ?, ?, ?)")) {
            connection.setAutoCommit(false);
            saveUser.setString(1, name);
            saveUser.setString(2, lastName);
            saveUser.setByte(3,age);
            saveUser.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }


    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement removeUser = connection.prepareStatement("delete from Users where id = ?")) {
            connection.setAutoCommit(false);
            removeUser.setLong(1, id);
            removeUser.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             Statement getAll = connection.createStatement()) {
            ResultSet resultSet = getAll.executeQuery("SELECT * FROM Users");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
        Statement cleanUsers = connection.createStatement()) {
            connection.setAutoCommit(false);
            cleanUsers.executeUpdate("TRUNCATE TABLE Users");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }
}
