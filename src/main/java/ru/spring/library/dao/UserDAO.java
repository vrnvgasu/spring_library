package ru.spring.library.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.spring.library.models.User;
import ru.spring.library.models.UserMapper;

@Component
public class UserDAO {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public UserDAO(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<User> index() {
    return jdbcTemplate.query("SELECT * FROM users", new UserMapper());
  }

  public Optional<User> show(Long id) {
    return jdbcTemplate.query(
            "SELECT * FROM users where id=?",
            new Object[]{id},
            new UserMapper()
        )
        .stream().findAny();
  }

  public Optional<User> findByFullName(String fullName) {
    return jdbcTemplate.query("SELECT * FROM users where full_name=?",
        new Object[]{fullName}, new UserMapper()
    ).stream().findAny();
  }

  public void update(Long id, User user) {
    jdbcTemplate.update("UPDATE users set full_name=?, birthday=? where id=?",
        user.getFullName(), user.getBirthday(), id
    );
  }

  public void save(User user) {
    jdbcTemplate.update("INSERT INTO users (full_name, birthday) VALUES(?, ?)",
        user.getFullName(), user.getBirthday()
    );
  }

  public void delete(Long id) {
    jdbcTemplate.update("DELETE FROM users WHERE id=?", id);
  }

}
