package ru.spring.library.util;

import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.spring.library.models.User;
import ru.spring.library.services.UserService;

@Component
public class UserUpdateValidator extends UserBaseValidator {

	@Autowired
	public UserUpdateValidator(UserService userService) {
		super(userService);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;

		if (user.getBirthday() > Calendar.getInstance().get(Calendar.YEAR)) {
			errors.rejectValue("birthday", "", "birthday is more than current year");
		}
	}

}
