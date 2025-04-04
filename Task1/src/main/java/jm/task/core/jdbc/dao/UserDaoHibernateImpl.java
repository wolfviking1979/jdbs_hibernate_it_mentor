package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Configuration configuration = new Configuration().configure();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS \"users\" " +
                    "(id SERIAL PRIMARY KEY, name VARCHAR(255), " +
                    "last_name VARCHAR(255), age INT)";
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Created table");

    }

    @Override
    public void dropUsersTable() {
        Configuration configuration = new Configuration().configure();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            String sql = "DROP TABLE if exists \"users\"";
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Delete table");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Configuration configuration = new Configuration().configure();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            user.setId(generateId());
            session.persist(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("User added");
    }

    private long generateId() {
        // Implement your logic to generate a unique ID
        // For example, you can use a counter or a UUID generator
        // Here, we'll use a simple counter for demonstration purposes
        return System.currentTimeMillis();
    }


    @Override
    public void removeUserById(long id) {
        Configuration configuration = new Configuration().configure();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.find(User.class, id);
            session.remove(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Delete table");
    }

    @Override
    public List<User> getAllUsers() {
        Configuration configuration = new Configuration().configure();
        List<User> userList  = new ArrayList<>();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from User");
            userList = query.getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Delete user");
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Configuration configuration = new Configuration().configure();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            String sql = "TRUNCATE TABLE \"users\"";
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Table cleaned");
    }
}
