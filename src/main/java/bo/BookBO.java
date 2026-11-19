package bo;

import dto.BookDTO;
import java.util.List;

public interface BookBO {
    boolean saveBook(BookDTO dto);
    boolean updateBook(BookDTO dto);
    boolean deleteBook(String id);
    BookDTO searchBook(String id);
    List<BookDTO> getAllBooks();
}