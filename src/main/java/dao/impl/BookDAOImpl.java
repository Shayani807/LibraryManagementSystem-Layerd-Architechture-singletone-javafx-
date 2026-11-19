package dao.impl;

import dao.BookDAO;
import Util.HibernateUtil;
import entity.Book;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class BookDAOImpl implements BookDAO {

    @Override
    public boolean save(Book book) {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        session.save(book);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Book book) {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        session.update(book);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        Book book = session.get(Book.class, id);
        if (book != null) {
            session.delete(book);
        }
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public Book search(String id) {
        Session session = HibernateUtil.getSession();
        Book book = session.get(Book.class, id);
        session.close();
        return book;
    }

    @Override
    public List<Book> getAll() {
        Session session = HibernateUtil.getSession();
        List<Book> list = session.createQuery("FROM Book", Book.class).list();
        session.close();
        return list;
    }
}