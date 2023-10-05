package grauebcf.schoolapppro.validator;

import grauebcf.schoolapppro.dto.UserRegisterDTO;
import grauebcf.schoolapppro.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


import java.util.Objects;

@Component
public class UserValidator implements Validator {

    private final IUserService userService;

    @Autowired
    public UserValidator(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserRegisterDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegisterDTO userToRegister = (UserRegisterDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "empty");
        if (userToRegister.getEmail().length() < 3 || userToRegister.getEmail().length() > 32) {
            errors.rejectValue("email", "size");
        }
        if (userService.isEmailTaken(userToRegister.getEmail())) {
            errors.rejectValue("email", "duplicate");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "empty");
        if (userToRegister.getPassword().length() < 3 || userToRegister.getPassword().length() > 32) {
            errors.rejectValue("password", "size");
        }
    }
}
