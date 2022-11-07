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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import ru.spring.library.models.Book;
import ru.spring.library.models.User;
import ru.spring.library.services.BookService;
import ru.spring.library.services.UserService;
import ru.spring.library.util.BookValidator;

@Controller
@RequestMapping("/books")
public class BookController {

	private final BookService bookService;

	private final UserService userService;

	private final BookValidator bookValidator;

	@Autowired
	public BookController(BookService bookService, UserService userService, BookValidator bookValidator) {
		this.bookService = bookService;
		this.userService = userService;
		this.bookValidator = bookValidator;
	}

	@GetMapping()
	public String index(@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "books_per_page", required = false) Integer booksPerPage,
			@RequestParam(name = "sort_by_year", required = false) Boolean sortByYear,
			Model model) {
		model.addAttribute("books", bookService.index(page, booksPerPage, sortByYear));

		return "book/index";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable("id") Long id, Model model, @ModelAttribute("user") User user) {
		Optional<Book> optionalBook = bookService.show(id);

		if (optionalBook.isEmpty()) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Book with id: " + id + " not found");
		}

		Book book = optionalBook.get();

		model.addAttribute("book", book);

		if (book.getUserId() == null) {
			model.addAttribute("users", userService.index());
		} else {
			Optional<User> bookUser = userService.findByBookId(optionalBook.get().getId());

			if (bookUser.isEmpty()) {
				model.addAttribute("users", userService.index());
			} else {
				model.addAttribute("bookUser", bookUser.get());
			}
		}

		return "book/show";
	}

	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable("id") Long id) {
		Optional<Book> optionalUser = bookService.show(id);

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

		bookService.update(id, book);

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

		bookService.save(book);

		return "redirect:/books";
	}

	@PostMapping("/{id}/person")
	public String addUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
		bookService.addUser(user.getId(), id);

		return "redirect:/books/" + id;
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") Long id) {
		bookService.delete(id);

		return "redirect:/books";
	}

	@DeleteMapping("/{id}/free")
	public String getFree(@PathVariable("id") Long id) {
		bookService.getFree(id);

		return "redirect:/books/" + id;
	}

	@GetMapping("/search")
	public String search(@RequestParam(name = "title") String title, Model model) {
		model.addAttribute("books", bookService.search(title));

		return "book/search";
	}

}
