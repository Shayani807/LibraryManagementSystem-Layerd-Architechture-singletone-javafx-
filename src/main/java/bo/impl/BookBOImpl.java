package bo.impl;

import bo.BookBO;
import dao.BookDAO;
import dao.impl.BookDAOImpl;
import dto.BookDTO;
import entity.Book;
import java.util.ArrayList;
import java.util.List;

public class BookBOImpl implements BookBO {

    private BookDAO bookDAO = new BookDAOImpl();

    @Override
    public boolean saveBook(BookDTO dto) {

        Book book = new Book(
                dto.getId(),
                dto.getTitle(),
                dto.getAuthor(),
                dto.getCategory(),
                Integer.parseInt(dto.getPublishedYear()),
                dto.getQuantity()
        );
        return bookDAO.save(book);
    }

    @Override
    public boolean updateBook(BookDTO dto) {
        Book book = new Book(
                dto.getId(),
                dto.getTitle(),
                dto.getAuthor(),
                dto.getCategory(),
                Integer.parseInt(dto.getPublishedYear()),
                dto.getQuantity()
        );
        return bookDAO.update(book);
    }

    @Override
    public boolean deleteBook(String id) {
        return bookDAO.delete(id);
    }

    @Override
    public BookDTO searchBook(String id) {
        Book book = bookDAO.search(id);
        if (book != null) {

            return new BookDTO(
                    book.getIsbn(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getCategory(),
                    String.valueOf(book.getPublishedYear()),
                    book.getQuantity()
            );
        }
        return null;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> list = bookDAO.getAll();
        List<BookDTO> dtoList = new ArrayList<>();
        for (Book book : list) {
            dtoList.add(new BookDTO(
                    book.getIsbn(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getCategory(),
                    String.valueOf(book.getPublishedYear()),
                    book.getQuantity()
            ));
        }
        return dtoList;
    }
}