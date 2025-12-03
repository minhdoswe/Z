package minhdoswe.socialnetwork.z.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    private static final String USERNAME_PATTERN =
            "^(?!\\d+$)[A-Za-z][A-Za-z0-9_]{2,19}$";

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (username == null) {
            return false;
        }
        return username.matches(USERNAME_PATTERN);
    }
}
