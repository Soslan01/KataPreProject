package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.jboss.logging.Logger;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static Logger LOG = Logger.getLogger(Util.class);
    private static final SessionFactory sessionFactory;

    //Логика подключения DriverManager и Hibernate;
    static {
        try {
            //DriverManager для подключения напрямую к JDBC;
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            //SessionFactory для подключения к Hibernate;
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/myDBTest");
            configuration.setProperty("hibernate.connection.username", "root");
            configuration.setProperty("hibernate.connection.password", "sos100398");
            configuration.setProperty("dialect", "org.hibernate.dialect.MySQL8Dialect");
            configuration.addAnnotatedClass(User.class);

            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable e) {
            LOG.error("Не удалось выполнить подключение в базе данных: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getDBConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDBTest", "root", "sos100398");
            if(!connection.isClosed()) {
                LOG.info("Соединение с БД установлено!");
            }
            return connection;
        } catch (SQLException e) {
            LOG.error("Не удалось загрузить класс драйвера!");
            throw e;
        }
    }

    public static Session getHibernateSession() throws SQLException {
        Session session = sessionFactory.openSession();
        if (session.isConnected()) {
            LOG.info("Соединение с БД установлено!");
        } else {
            LOG.error("Не удалось загрузить класс драйвера!");
            throw new SQLException("Hibernate cannot connect to database!");
        }

        return session;
    }

    // реализуйте настройку соеденения с БД


//    public static SessionFactory sessionFactory;
//
//    static {
//        try {
//            Properties prop = new Properties();
//            prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/myDBTest");
//            prop.setProperty("hibernate.connection.username", "root");
//            prop.setProperty("hibernate.connection.password", "sos100398");
//            prop.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
//        }
//    }
}