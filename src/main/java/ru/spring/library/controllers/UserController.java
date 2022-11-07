package ru.spring.library.controllers;

import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;
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
import ru.spring.library.models.Book;
import ru.spring.library.models.User;
import ru.spring.library.services.BookService;
import ru.spring.library.services.UserService;
import ru.spring.library.util.UserCreateValidator;
import ru.spring.library.util.UserUpdateValidator;

@Controller
@RequestMapping("/people")
public class UserController {

	private final UserService userService;

	private final BookService bookService;

	private final UserCreateValidator userCreateValidator;

	private final UserUpdateValidator userUpdateValidator;

	public UserController(UserService userService, BookService bookService, UserCreateValidator userCreateValidator,
			UserUpdateValidator userUpdateValidator) {
		this.userService = userService;
		this.bookService = bookService;
		this.userCreateValidator = userCreateValidator;
		this.userUpdateValidator = userUpdateValidator;
	}

	@GetMapping()
	public String index(Model model) {
		model.addAttribute("users", userService.index());

		return "user/index";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable("id") Long id, Model model) {
		Optional<User> optionalUser = userService.show(id);

		if (optionalUser.isEmpty()) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User with id: " + id + " not found");
		}

		model.addAttribute("user", optionalUser.get());
		Set<Book> books = bookService.getByUser(id);

		if (!books.isEmpty()) {
			model.addAttribute("books", books);
		}

		return "user/show";
	}

	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable("id") Long id) {
		Optional<User> optionalUser = userService.show(id);

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

		userService.update(id, user);

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

		userService.save(user);

		return "redirect:/people";
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") Long id) {
		userService.delete(id);

		return "redirect:/people";
	}

}
