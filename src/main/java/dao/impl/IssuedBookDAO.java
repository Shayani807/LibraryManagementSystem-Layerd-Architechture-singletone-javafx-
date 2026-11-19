package dao;

import dto.BorrowingHistoryDTO;
import entity.IssuedBook;
import java.util.List;

public interface IssuedBookDAO {
    boolean save(IssuedBook issuedBook);
    List<IssuedBook> getAll();
    boolean returnBook(int issueId);
    List<BorrowingHistoryDTO> getAllHistory();
}