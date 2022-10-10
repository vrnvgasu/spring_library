package ru.spring.library.controllers;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import ru.spring.library.dao.BookDAO;
import ru.spring.library.dao.UserDAO;
import ru.spring.library.models.Book;
import ru.spring.library.models.User;
import ru.spring.library.util.BookValidator;

@Controller
@RequestMapping("/books")
public class BookController {

  private final BookDAO bookDAO;

  private final UserDAO userDAO;

  private final BookValidator bookValidator;

  @Autowired
  public BookController(BookDAO userDAO, UserDAO userDAO1, BookValidator bookValidator) {
    this.bookDAO = userDAO;
    this.userDAO = userDAO1;
    this.bookValidator = bookValidator;
  }

  @GetMapping()
  public String index(Model model) {
    model.addAttribute("books", bookDAO.index());

    return "book/index";
  }

  @GetMapping("/{id}")
  public String show(@PathVariable("id") Long id, Model model, @ModelAttribute("user") User user) {
    Optional<Book> optionalBook = bookDAO.show(id);

    if (optionalBook.isEmpty()) {
      throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Book with id: " + id + " not found");
    }

    model.addAttribute("book", optionalBook.get());
    Optional<User> bookUser = userDAO.findByBookId(optionalBook.get().getId());

    if (bookUser.isEmpty()) {
      model.addAttribute("users", userDAO.index());
    } else {
      model.addAttribute("bookUser", bookUser.get());
    }

    return "book/show";
  }

  @GetMapping("/{id}/edit")
  public String edit(Model model, @PathVariable("id") Long id) {
    Optional<Book> optionalUser = bookDAO.show(id);

    if (optionalUser.isEmpty()) {
      throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Book with id: " + id + " not found");
    }

    model.addAttribute("book", optionalUser.get());

    return "book/edit";
  }

  @PatchMapping("/{id}")
  public String update(@ModelAttribute("book") @Valid Book book,
      BindingResult bindingResult,
      @PathVariable("id") Long id) {
    bookValidator.validate(book, bindingResult);

    if (bindingResult.hasErrors()) {
      return "book/edit";
    }

    bookDAO.update(id, book);

    return "redirect:/books/" + id;
  }

  @GetMapping("/new")
  public String add(@ModelAttribute("book") Book book) {
    return "book/new";
  }

  @PostMapping()
  public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
    bookValidator.validate(book, bindingResult);

    if (bindingResult.hasErrors()) {
      return "book/new";
    }

    bookDAO.save(book);

    return "redirect:/books";
  }

  @PostMapping("/{id}/person")
  public String addUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
    bookDAO.addUser(user.getId(), id);

    return "redirect:/books/" + id;
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable("id") Long id) {
    bookDAO.delete(id);

    return "redirect:/books";
  }

  @DeleteMapping("/{id}/free")
  public String getFree(@PathVariable("id") Long id) {
    bookDAO.getFree(id);

    return "redirect:/books/" + id;
  }

}
