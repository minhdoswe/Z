package minhdoswe.socialnetwork.z.modules.user.internal.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UsernameValidator implements ConstraintValidator<Username, String> {

    private static final String USERNAME_PATTERN =
            "^[a-z](?:[a-z0-9]|_(?![_.])|\\.(?![_.]))*$";

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (username == null || username.isBlank()) return true;


        if (username.matches(USERNAME_PATTERN)) {
            log.info("VALID");
        } else {
            log.info("INVALID");
        }



        return username.matches(USERNAME_PATTERN);
    }
}
