package bo;

import dto.BorrowingHistoryDTO;
import dto.IssueDTO;
import java.util.List;

public interface IssuedBookBO {
    boolean issueBook(IssueDTO dto);
    List<IssueDTO> getAllIssuedBooks();
    boolean returnBook(int issueId);
    List<BorrowingHistoryDTO> getAllHistory();
}