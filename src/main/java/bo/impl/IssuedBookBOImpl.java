package bo.impl;

import bo.IssuedBookBO;
import dao.IssuedBookDAO;
import dao.impl.IssuedBookDAOImpl;
import dto.BorrowingHistoryDTO;
import dto.IssueDTO;
import entity.Book;
import entity.IssuedBook;
import entity.Member;
import java.util.ArrayList;
import java.util.List;

public class IssuedBookBOImpl implements IssuedBookBO {

    IssuedBookDAO issuedBookDAO = new IssuedBookDAOImpl();

    @Override
    public boolean issueBook(IssueDTO dto) {
        IssuedBook ib = new IssuedBook();
        Member m = new Member();
        m.setMemberId(dto.getMemberId());
        ib.setMember(m);
        Book b = new Book();
        b.setIsbn(dto.getIsbn());
        ib.setBook(b);
        ib.setIssueDate(dto.getIssueDate());
        ib.setDueDate(dto.getDueDate());
        ib.setReturnDate(dto.getReturnDate());
        ib.setStatus(dto.getStatus());
        return issuedBookDAO.save(ib);
    }

    @Override
    public List<IssueDTO> getAllIssuedBooks() {
        List<IssueDTO> list = new ArrayList<>();
        for (IssuedBook ib : issuedBookDAO.getAll()) {
            list.add(new IssueDTO(
                    ib.getIssueId(),
                    ib.getMember() != null ? ib.getMember().getMemberId() : "",
                    ib.getBook() != null ? ib.getBook().getIsbn() : "",
                    ib.getBook() != null ? ib.getBook().getTitle() : "",
                    ib.getIssueDate(),
                    ib.getDueDate(),
                    ib.getReturnDate(),
                    ib.getStatus()
            ));
        }
        return list;
    }

    @Override
    public boolean returnBook(int issueId) {
        return issuedBookDAO.returnBook(issueId);
    }

    @Override
    public List<BorrowingHistoryDTO> getAllHistory() {
        return issuedBookDAO.getAllHistory();
    }
}