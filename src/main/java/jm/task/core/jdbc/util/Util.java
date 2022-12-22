package jm.task.core.jdbc.util;

import org.jboss.logging.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static Logger LOG = Logger.getLogger(Util.class);
    // реализуйте настройку соеденения с БД
    public static Connection getDBConnection () {
        Connection connection = null;
        try {

            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDBTest", "root", "sos100398");

            if(!connection.isClosed()) {
                LOG.info("Соединение с БД установлено!");
            }

        } catch (SQLException e) {
            LOG.error("Не удалось загрузить класс драйвера!");
        }
        return connection;
    }
}