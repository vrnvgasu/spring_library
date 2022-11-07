package ru.spring.library.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spring.library.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	Optional<Book> findByTitleAndAuthorAndPublishedAt(String title, String author, Integer publishedAt);

	Set<Book> findByUserId(Long userId);

	List<Book> findAllByOrderByPublishedAtAsc();

	List<Book> findAllByOrderByTitleAsc();

	@Query(value = "select b from Book b "
			+ "left join fetch b.user "
			+ "where upper(b.title) like :title order by b.title")
	List<Book> findByTitleLike(@Param("title") String title, Pageable page);

}
