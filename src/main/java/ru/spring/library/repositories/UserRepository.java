package ru.spring.library.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spring.library.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByFullName(String fullName);

}
