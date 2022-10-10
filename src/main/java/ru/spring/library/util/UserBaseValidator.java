package ru.spring.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import ru.spring.library.dao.UserDAO;
import ru.spring.library.models.User;

@Component
abstract public class UserBaseValidator implements Validator {

  protected final UserDAO userDAO;

  @Autowired
  public UserBaseValidator(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return User.class.equals(clazz);
  }

}
