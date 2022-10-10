package ru.spring.library.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<User> {

  @Override
  public User mapRow(ResultSet rs, int rowNum) throws SQLException {
    User user = new User();
    user.setId(rs.getLong("id"));
    user.setFullName(rs.getString("full_name"));
    user.setBirthday(rs.getInt("birthday"));

    return user;
  }

}
