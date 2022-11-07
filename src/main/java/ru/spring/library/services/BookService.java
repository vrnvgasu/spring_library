package ru.spring.library.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

	public List<Book> index(Integer page, Integer booksPerPage, Boolean sortByYear) {
		Pageable pageRequest = null;

		// пагинация
		if (!Objects.isNull(page) && !Objects.isNull(booksPerPage) && Objects.isNull(sortByYear)) {
			pageRequest = PageRequest.of(page, booksPerPage, Sort.by("title"));
			// пагинация + сортировка
		} else if (!Objects.isNull(page) && !Objects.isNull(booksPerPage) && !Objects.isNull(sortByYear) && sortByYear) {
			pageRequest = PageRequest.of(page, booksPerPage, Sort.by("publishedAt"));
			// сортировка
		} else if ((Objects.isNull(page) || !Objects.isNull(booksPerPage)) && !Objects.isNull(sortByYear) && sortByYear) {
			return bookRepository.findAllByOrderByPublishedAtAsc();
		}

		if (!Objects.isNull(pageRequest)) {
			return bookRepository.findAll(pageRequest).getContent();
		}

		// вывод по названию
		return bookRepository.findAllByOrderByTitleAsc();
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

	public List<Book> search(String title) {
		if (title.isEmpty()) {
			return new ArrayList<>();
		}

		Pageable topTen = PageRequest.of(0, 10);
		return bookRepository.findByTitleLike("%" + title.toUpperCase() + "%", topTen);
	}

}
