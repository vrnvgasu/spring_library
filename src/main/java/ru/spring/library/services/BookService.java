package ru.spring.library.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spring.library.models.Book;
import ru.spring.library.models.User;
import ru.spring.library.repositories.BookRepository;
import ru.spring.library.repositories.UserRepository;

@Service
@Transactional(readOnly = true)
public class BookService {

	private final BookRepository bookRepository;

	private final UserRepository userRepository;

	public BookService(BookRepository bookRepository, UserRepository userRepository) {
		this.bookRepository = bookRepository;
		this.userRepository = userRepository;
	}

	public List<Book> index() {
		return bookRepository.findAll();
	}

	public Optional<Book> show(Long id) {
		return bookRepository.findById(id);
	}

	public Optional<Book> findByTitleAndAuthorAndPublishedAt(String title, String author, Integer publishedAt) {
		return bookRepository.findByTitleAndAuthorAndPublishedAt(title, author, publishedAt);
	}

	@Transactional
	public void update(Long id, Book book) {
		Book oldBook = bookRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(".update: Book with id " + id + " not found"));
		book.setTitle(book.getTitle());
		book.setAuthor(book.getAuthor());
		book.setPublishedAt(book.getPublishedAt());
		bookRepository.save(oldBook);
	}

	@Transactional
	public void save(Book book) {
		bookRepository.save(book);
	}

	@Transactional
	public void delete(Long id) {
		bookRepository.deleteById(id);
	}

	@Transactional
	public void addUser(Long userId, Long bookId) {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new RuntimeException(".addUser: Book with id " + bookId + " not found"));
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException(".addUser: User with id " + userId + " not found"));
		book.setUser(user);
		bookRepository.save(book);
	}

	@Transactional
	public void getFree(Long bookId) {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new RuntimeException(".getFree: Book with id " + bookId + " not found"));
		book.setUser(null);
		bookRepository.save(book);
	}

	public Set<Book> getByUser(Long userId) {
		return bookRepository.findByUserId(userId);
	}

}
