package ru.spring.library.controllers;

import java.util.List;
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
import ru.spring.library.util.UserCreateValidator;
import ru.spring.library.util.UserUpdateValidator;

@Controller
@RequestMapping("/people")
public class UserController {

  private final UserDAO userDAO;

  private final BookDAO bookDAO;

  private final UserCreateValidator userCreateValidator;

  private final UserUpdateValidator userUpdateValidator;

  @Autowired
  public UserController(UserDAO userDAO, BookDAO bookDAO, UserCreateValidator userValidator, UserUpdateValidator userUpdateValidator) {
    this.userDAO = userDAO;
    this.bookDAO = bookDAO;
    this.userCreateValidator = userValidator;
    this.userUpdateValidator = userUpdateValidator;
  }

  @GetMapping()
  public String index(Model model) {
    model.addAttribute("users", userDAO.index());

    return "user/index";
  }

  @GetMapping("/{id}")
  public String show(@PathVariable("id") Long id, Model model) {
    Optional<User> optionalUser = userDAO.show(id);

    if (optionalUser.isEmpty()) {
      throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User with id: " + id + " not found");
    }

    model.addAttribute("user", optionalUser.get());
    List<Book> books = bookDAO.getByUser(id);

    if (!books.isEmpty()) {
      model.addAttribute("books", books);
    }

    return "user/show";
  }

  @GetMapping("/{id}/edit")
  public String edit(Model model, @PathVariable("id") Long id) {
    Optional<User> optionalUser = userDAO.show(id);

    if (optionalUser.isEmpty()) {
      throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User with id: " + id + " not found");
    }

    model.addAttribute("user", optionalUser.get());

    return "user/edit";
  }

  @PatchMapping("/{id}")
  public String update(@ModelAttribute("user") @Valid User user,
      BindingResult bindingResult,
      @PathVariable("id") Long id) {
    userUpdateValidator.validate(user, bindingResult);

    if (bindingResult.hasErrors()) {
      return "user/edit";
    }

    userDAO.update(id, user);

    return "redirect:/people/" + id;
  }

  @GetMapping("/new")
  public String add(@ModelAttribute("user") User user) {
    return "user/new";
  }

  @PostMapping()
  public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
    userCreateValidator.validate(user, bindingResult);

    if (bindingResult.hasErrors()) {
      return "user/new";
    }

    userDAO.save(user);

    return "redirect:/people";
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable("id") Long id) {
    userDAO.delete(id);

    return "redirect:/people";
  }

}
