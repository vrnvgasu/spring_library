package ru.spring.library.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.spring.library.models.Book;
import ru.spring.library.models.BookMapper;

@Component
public class BookDAO {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public BookDAO(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Book> index() {
    return jdbcTemplate.query("SELECT * FROM books", new BookMapper());
  }

  public Optional<Book> show(Long id) {
    return jdbcTemplate.query(
            "SELECT * FROM books where id=?",
            new Object[]{id},
            new BookMapper()
        )
        .stream().findAny();
  }

  public Optional<Book> findByTitleAndAuthorAndPublishedAt(String title, String author, Integer publishedAt) {
    return jdbcTemplate.query("SELECT * FROM books where title=? and author=? and published_at=?",
        new Object[]{title, author, publishedAt}, new BookMapper()
    ).stream().findAny();
  }

  public void update(Long id, Book book) {
    jdbcTemplate.update("UPDATE books set title=?, author=?, published_at=? where id=?",
        book.getTitle(), book.getAuthor(), book.getPublishedAt(), id
    );
  }

  public void save(Book book) {
    jdbcTemplate.update("INSERT INTO books (title, author, published_at) VALUES(?, ?, ?)",
        book.getTitle(), book.getAuthor(), book.getPublishedAt()
    );
  }

  public void delete(Long id) {
    jdbcTemplate.update("DELETE FROM books WHERE id=?", id);
  }

  public void addUser(Long userId, Long BookId) {
    jdbcTemplate.update("INSERT INTO book_user (user_id, book_id) VALUES(?, ?)",
        userId, BookId
    );
  }

  public void getFree(Long bookId) {
    jdbcTemplate.update("DELETE FROM book_user WHERE book_id=?", bookId);
  }

  public List<Book> getByUser(Long userId) {
    return jdbcTemplate.query("SELECT * FROM books "
        + "join book_user bu on books.id = bu.book_id "
            + "where bu.user_id=?",
        new Object[]{userId},
        new BookMapper());
  }

}
