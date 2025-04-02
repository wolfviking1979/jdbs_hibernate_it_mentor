package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        Configuration configuration = new Configuration().configure();
//        try(var sessionFactory = configuration.buildSessionFactory();
//        var session = sessionFactory.openSession()) {
//            session.beginTransaction();
//
//
//            session.getTransaction().commit();
//        }
//
        userService.saveUser("Ivan", "Ivanov", (byte) 35);
    }
}
