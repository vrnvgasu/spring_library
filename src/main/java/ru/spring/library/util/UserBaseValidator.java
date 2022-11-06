package ru.spring.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import ru.spring.library.models.User;
import ru.spring.library.services.UserService;

@Component
abstract public class UserBaseValidator implements Validator {

	protected final UserService userService;

	@Autowired
	public UserBaseValidator(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

}
