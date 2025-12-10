package minhdoswe.socialnetwork.z.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class UsernameValidator implements ConstraintValidator<Username, String> {

    private static final String USERNAME_PATTERN =
            "^(?!\\d+$)[A-Za-z][A-Za-z0-9_]$";

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (username == null || username.isBlank()) return true;
        return username.matches(USERNAME_PATTERN);
    }
}
