package dao.impl;

import Util.HibernateUtil;
import dao.IssuedBookDAO;
import dto.BorrowingHistoryDTO;
import entity.IssuedBook;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IssuedBookDAOImpl implements IssuedBookDAO {

    @Override
    public boolean save(IssuedBook ib) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(ib);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<IssuedBook> getAll() {
        List<IssuedBook> list = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT ib FROM IssuedBook ib JOIN FETCH ib.book JOIN FETCH ib.member";
            Query<IssuedBook> query = session.createQuery(hql, IssuedBook.class);
            list = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean returnBook(int issueId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            IssuedBook ib = session.get(IssuedBook.class, issueId);
            if (ib == null) return false;
            ib.setStatus("RETURNED");
            ib.setReturnDate(LocalDate.now());
            session.merge(ib);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<BorrowingHistoryDTO> getAllHistory() {
        List<BorrowingHistoryDTO> dtoList = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<IssuedBook> list = session.createQuery(
                    "FROM IssuedBook i " +
                            "JOIN FETCH i.member " +
                            "JOIN FETCH i.book " +
                            "ORDER BY i.issueDate DESC",
                    IssuedBook.class
            ).list();

            for (IssuedBook issued : list) {
                dtoList.add(new BorrowingHistoryDTO(
                        issued.getIssueId(),
                        issued.getMember().getMemberId(),
                        issued.getMember().getFullName(),
                        issued.getBook().getIsbn(),
                        issued.getBook().getTitle(),
                        issued.getIssueDate(),
                        issued.getDueDate(),
                        issued.getReturnDate(),
                        issued.getStatus()
                ));
            }
        }
        return dtoList;
    }
}