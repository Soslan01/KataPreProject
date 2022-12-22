package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;

import javax.transaction.Transactional;
import java.sql.*;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {

    private static Logger LOG = Logger.getLogger(UserDaoJDBCImpl.class);

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() throws SQLException {

        Transaction transaction = null;
        Session session = null;
        String createTableSQL = "CREATE TABLE IF NOT EXISTS USERS("
                + "ID INT(5) NOT NULL PRIMARY KEY AUTO_INCREMENT UNIQUE, "
                + "USERNAME VARCHAR(26) NOT NULL, "
                + "LASTNAME VARCHAR(26) NOT NULL, "
                + "AGE INT(3) NOT NULL)";

        try {
            session = Util.getHibernateSession();
            transaction = session.beginTransaction();

            //выполняется SQL запрос в консоли;
            Query query = session.createSQLQuery(createTableSQL);
            query.executeUpdate();
            transaction.commit();

            LOG.info("Table \"users\" is created!");
        } catch (SQLException | HibernateException e) {
            transaction.rollback();
            LOG.error(e.getMessage());
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() throws SQLException {
        Transaction transaction = null;
        Session session = null;

        try {
            session = Util.getHibernateSession();
            transaction = session.beginTransaction();

            Query query = session.createSQLQuery("DROP TABLE IF EXISTS myDBTest.USERS");
            query.executeUpdate();
            transaction.commit();

            LOG.info("Table \"users\" is deleted!");
        } catch (SQLException | HibernateException e) {
            transaction.rollback();
            LOG.error(e.getMessage());
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        Transaction transaction = null;
        Session session = null;

        try {
            session = Util.getHibernateSession();
            transaction = session.beginTransaction();

            User newUser = new User(name, lastName, age);
            session.save(newUser);
            transaction.commit();

            LOG.info("User с именем - " + name + " добавлен в базу данных!");
        } catch (SQLException | HibernateException e) {
            transaction.rollback();
            LOG.error(e.getMessage());
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) throws SQLException {
        Transaction transaction = null;
        Session session = null;

        try {
            session = Util.getHibernateSession();
            transaction = session.beginTransaction();

            User newUser = new User();
            newUser.setId(id);
            session.delete(newUser);
            transaction.commit();

            LOG.info("User с id - " + id + " удален из базы!");
        } catch (SQLException | HibernateException e) {
            transaction.rollback();
            LOG.error(e.getMessage());
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        Transaction transaction = null;
        Session session = null;

        try {
            session = Util.getHibernateSession();
            transaction = session.beginTransaction();

            List<User> allUsers = session.createQuery("select u from User u", User.class).getResultList();
            transaction.commit();

            LOG.info("Все записи из таблицы User успешно получены!");
            return allUsers;
        } catch (SQLException | HibernateException e) {
            transaction.rollback();
            LOG.error(e.getMessage());
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void cleanUsersTable() throws SQLException {
        Transaction transaction = null;
        Session session = null;

        try {
            session = Util.getHibernateSession();
            transaction = session.beginTransaction();

            Query query = session.createSQLQuery("TRUNCATE TABLE USERS");
            query.executeUpdate();
            transaction.commit();

            LOG.info("Таблица \"users\" удалена успешно!");
        } catch (SQLException | HibernateException e) {
            transaction.rollback();
            LOG.error(e.getMessage());
            throw e;
        } finally {
            session.close();
        }
    }
}
