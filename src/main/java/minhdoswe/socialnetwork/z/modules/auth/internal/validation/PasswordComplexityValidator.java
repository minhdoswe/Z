package minhdoswe.socialnetwork.z.modules.auth.internal.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordComplexityValidator implements ConstraintValidator<PasswordComplexity, String> {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*@#$%^&+=!])(?=\\S+$)$";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        //let @NotBlank handle
        if (password == null || password.isBlank()) {
            return true;
        }

        boolean valid = true;
        constraintValidatorContext.disableDefaultConstraintViolation();

        if (!password.matches(".*[A-Z].*")) {
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(
                            "{validation.user.password.complexity.uppercase}"
                    )
                    .addConstraintViolation();
            valid = false;
        }

        if (!password.matches(".*[0-9].*")) {
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(
                            "{validation.user.password.complexity.digit}"
                    )
                    .addConstraintViolation();
            valid = false;
        }

        if (!password.matches(".*[a-z].*")) {
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(
                            "{validation.user.password.complexity.lowercase}"
                    )
                    .addConstraintViolation();
            valid = false;
        }

        if (!password.matches(".*[*@#$%^&+=!].*")) {
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(
                            "{validation.user.password.complexity.special}"
                    )
                    .addConstraintViolation();
            valid = false;
        }

        return valid;
    }
}
