package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String create_table = "CREATE TABLE IF NOT EXISTS usersdb.users (\n" +
                "    id BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "    name VARCHAR(50),\n" +
                "    lastName VARCHAR(50),\n" +
                "    age TINYINT,\n" +
                "    PRIMARY KEY (id)\n" +
                ");";
        try (Connection connection = Util.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(create_table)) {
            preparedStatement.execute(create_table);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        String dropUsers = "DROP TABLE IF EXISTS users;";
        try (Connection connection = Util.getDbConnection();
             PreparedStatement ps = connection.prepareStatement(dropUsers)) {
            ps.execute(dropUsers);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insertUser = "INSERT INTO users (name, lastName, age) values (?, ?, ?)";
        try (Connection connection = Util.getDbConnection();
             PreparedStatement ps = connection.prepareStatement(insertUser)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);

            ps.executeUpdate();
            System.out.printf("\nUser с именем %s добавлен в базу данных", name);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String removeUser = "DELETE FROM users WHERE id = ?";
        try (Connection connection = Util.getDbConnection();
             PreparedStatement ps = connection.prepareStatement(removeUser)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        String selectAll = "SELECT * FROM users";
        try (Connection connection = Util.getDbConnection();
             PreparedStatement ps = connection.prepareStatement(selectAll)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                byte age = rs.getByte("age");
                User user = new User(name, lastName, age);
                allUsers.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return allUsers;
    }

    public void cleanUsersTable() {
        String truncateUsers = "TRUNCATE users;";
        try (Connection connection = Util.getDbConnection();
             PreparedStatement ps = connection.prepareStatement(truncateUsers)) {
            ps.execute(truncateUsers);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
