package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.util.Properties;

public class UtilHibernate {
    public static Properties properties = new Properties();

    private UtilHibernate() {
    }

    static {
        loadProperties();
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void loadProperties() {
        try( var inputStream = UtilJdbc.class
                .getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static final String DRIVER = "db.driver";
    private static final String URL = "db.url";
    private static final String USERNAME = "db.username";
    private static final String PASSWORD = "db.password";

    private static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                Properties settings = new Properties();

                settings.put(Environment.DRIVER, getProperty(DRIVER));
                settings.put(Environment.URL, getProperty(URL));
                settings.put(Environment.USER, getProperty(USERNAME));
                settings.put(Environment.PASS, getProperty(PASSWORD));
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                settings.put(Environment.SHOW_SQL, "false");
                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                System.out.println("Ошибка при создании SessionFactory");
                e.printStackTrace();

            }
        }
        return sessionFactory;
    }
}
