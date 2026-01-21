package minhdoswe.socialnetwork.z.modules.user.internal.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    private static final String PHONE_NUMBER_PATTERN =
            "^0[1-9][0-9]*$";

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            return true;
        }
        return phoneNumber.matches(PHONE_NUMBER_PATTERN);
    }
}
