package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.jboss.logging.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static Logger LOG = Logger.getLogger(UserDaoJDBCImpl.class);

    //Создание таблицы;
    public void createUsersTable() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS USERS("
                + "ID INT(5) NOT NULL PRIMARY KEY AUTO_INCREMENT UNIQUE, "
                + "USERNAME VARCHAR(26) NOT NULL, "
                + "LASTNAME VARCHAR(26) NOT NULL, "
                + "AGE INT(3) NOT NULL)";

        try (Connection connection = Util.getDBConnection(); Statement statement = connection.createStatement()) {
            //выполняется SQL запрос в консоли;
            statement.execute(createTableSQL);

            LOG.info("Table \"users\" is created!");
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    //Удаление таблицы;
    public void dropUsersTable() throws SQLException {
        try (Connection connection = Util.getDBConnection(); Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS myDBTest.USERS");

            LOG.info("Table \"users\" is deleted!");
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    //Добавление пользователя в таблицу;
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String insertTableSQL = "INSERT INTO myDBTest.USERS(USERNAME, LASTNAME, AGE) VALUES(?,?,?)";

        try (Connection connection = Util.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

            LOG.info("User с именем - " + name + " добавлен в базу данных!");
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    //Удаление пользователя по id;
    public void removeUserById(long id) throws SQLException {
        String removeUsersSQL = "DELETE FROM myDBTest.USERS WHERE id = ?";

        try (Connection connection = Util.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(removeUsersSQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            LOG.info("User с id - " + id + " удален из базы!");
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    //Вывод всех пользователей из таблицы;
    public List<User> getAllUsers() throws SQLException {
        List<User> allUsers = new ArrayList<>();
        String getUsersSQl = "SELECT ID, USERNAME, LASTNAME, AGE FROM myDBTest.USERS";

        try (Connection connection = Util.getDBConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(getUsersSQl)) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("USERNAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                allUsers.add(user);
            }

            LOG.info("Все записи из таблицы User успешно получены!");
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw e;
        }
        return allUsers;
    }

    //Удаление всех пользователей из таблицы;
    public void cleanUsersTable() throws SQLException {
        try (Connection connection = Util.getDBConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE USERS");

            LOG.info("Таблица \"users\" удалена успешно!");
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }
}
