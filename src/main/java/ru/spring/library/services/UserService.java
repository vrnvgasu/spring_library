package ru.spring.library.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spring.library.models.Book;
import ru.spring.library.models.User;
import ru.spring.library.repositories.BookRepository;
import ru.spring.library.repositories.UserRepository;

@Service
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;

	private final BookRepository bookRepository;

	public UserService(UserRepository userRepository, BookRepository bookRepository) {
		this.userRepository = userRepository;
		this.bookRepository = bookRepository;
	}

	public List<User> index() {
		return userRepository.findAll();
	}

	public Optional<User> show(Long id) {
		return userRepository.findById(id);
	}

	public Optional<User> findByFullName(String fullName) {
		return userRepository.findByFullName(fullName);
	}

	@Transactional
	public void update(Long id, User user) {
		User oldUser = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(".update: User with id " + id + " not found"));
		user.setFullName(user.getFullName());
		user.setBirthday(user.getBirthday());
		userRepository.save(oldUser);
	}

	@Transactional
	public void save(User user) {
		userRepository.save(user);
	}

	@Transactional
	public void delete(Long id) {
		userRepository.deleteById(id);
	}

	public Optional<User> findByBookId(Long bookId) {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new RuntimeException(".findByBookId: Book with id " + bookId + " not found"));
		return userRepository.findById(book.getUserId());
	}

}
