package Util;

import entity.Book;
import entity.IssuedBook;
import entity.Member;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {


    private static HibernateUtil instance;

    private static SessionFactory sessionFactory;


    private HibernateUtil() {
        sessionFactory = buildSessionFactory();
    }


    public static synchronized HibernateUtil getInstance() {
        if (instance == null) {
            instance = new HibernateUtil();
        }
        return instance;
    }

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration config = new Configuration();
            config.configure("hibernate.cfg.xml");


            config.addAnnotatedClass(Member.class);
            config.addAnnotatedClass(Book.class);
            config.addAnnotatedClass(IssuedBook.class);


            config.setProperty("hibernate.hbm2ddl.auto", "update");

            SessionFactory factory = config.buildSessionFactory();
            System.out.println("HIBERNATE CONNECTED SUCCESSFULLY!");
            return factory;

        } catch (Throwable ex) {
            System.err.println("HIBERNATE CONNECTION FAILED!");
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (instance == null) {
            getInstance();
        }
        return sessionFactory;
    }

    public static Session getSession() {
        return getSessionFactory().openSession();
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}