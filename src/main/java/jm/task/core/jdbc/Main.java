package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь

        UserService service = new UserServiceImpl();


        service.createUsersTable();

        //Добавление пользователей в таблицу;
        service.saveUser("Ivan", "Vasilievich", (byte) 34);
        service.saveUser("Leroy", "Dgenkins", (byte) 42);
        service.saveUser("Patric", "Glenkins", (byte) 17);
        service.saveUser("Muscle", "Globus", (byte) 31);

        //Вывод всех пользователей из таблицы;
        service.getAllUsers();

        //Удаление пользователя по id;
        service.removeUserById(1);

        //Удаление всех пользователей из таблицы;
        service.cleanUsersTable();

        //Удаление таблицы;
        service.dropUsersTable();
    }
}
