package dao;

import entity.Book;
import java.util.List;

public interface BookDAO {
    boolean save(Book book);
    boolean update(Book book);
    boolean delete(String id);
    Book search(String id);
    List<Book> getAll();
}