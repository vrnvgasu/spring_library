package ru.spring.library.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spring.library.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	Optional<Book> findByTitleAndAuthorAndPublishedAt(String title, String author, Integer publishedAt);

	Set<Book> findByUserId(Long userId);

	List<Book> findAllByOrderByPublishedAtAsc();

	List<Book> findAllByOrderByTitleAsc();

}
