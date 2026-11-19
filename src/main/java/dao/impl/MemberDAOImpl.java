package dao.impl;
import Util.HibernateUtil;
import dao.MemberDAO;
import entity.Member;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class MemberDAOImpl implements MemberDAO {
    @Override
    public boolean save(Member member) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(member);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public List<Member> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Member", Member.class).list();
        }
    }
    @Override
    public boolean update(Member member) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(member);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean delete(String memberId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Member m = session.get(Member.class, memberId);
            if (m != null) session.remove(m);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }
}