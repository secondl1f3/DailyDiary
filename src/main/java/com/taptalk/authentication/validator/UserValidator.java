package com.taptalk.authentication.validator;

import com.taptalk.authentication.model.User;
import com.taptalk.authentication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        Pattern specialCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }

        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        /*if (!user.getPassword().matches("(?=.*[a-z])")){
            errors.rejectValue("password","Error.password.lowercase");
        }

        if (!user.getPassword().matches("(?=.*[A-Z])")){
            errors.rejectValue("password","Error.password.uppercase");
        }

        if (!user.getPassword().matches("(?=.*[0-9])")){
            errors.rejectValue("password","Error.password.lowercase");
        }

        if (!user.getPassword().matches("(?=.*\\W)")){
            errors.rejectValue("password","Error.password.lowercase");
        }*/

        if (!specialCharPatten.matcher(user.getPassword()).find()) {
            errors.rejectValue("password","Error.password.special");

        }
        if (!UpperCasePatten.matcher(user.getPassword()).find()) {
            errors.rejectValue("password","Error.password.uppercase");

        }
        if (!lowerCasePatten.matcher(user.getPassword()).find()) {
            errors.rejectValue("password","Error.password.lowercase");

        }
        if (!digitCasePatten.matcher(user.getPassword()).find()) {
            errors.rejectValue("password","Error.password.digit");

        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}
