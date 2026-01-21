package minhdoswe.socialnetwork.z.modules.user.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NameValidator implements ConstraintValidator<Name, String> {

    private static final String NAME_PATTERN = "^[\\p{L}][\\p{L} .'-]*$";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.isBlank()) {
            return true;
        }

        return s.matches(NAME_PATTERN);
    }
}
