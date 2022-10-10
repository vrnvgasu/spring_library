package ru.spring.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.spring.library.dao.BookDAO;
import ru.spring.library.models.Book;
import ru.spring.library.models.User;

@Component
public class BookValidator implements Validator {

  protected final BookDAO bookDAO;

  @Autowired
  public BookValidator(BookDAO bookDAO) {
    this.bookDAO = bookDAO;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return User.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    Book book = (Book) target;

    if (bookDAO.findByTitleAndAuthorAndPublishedAt(book.getTitle(), book.getAuthor(), book.getPublishedAt()).isPresent()) {
      errors.rejectValue("title", "", "Такая книга уже существует");
      errors.rejectValue("author", "", "Такая книга уже существует");
      errors.rejectValue("published_at", "", "Такая книга уже существует");
    }
  }

}
