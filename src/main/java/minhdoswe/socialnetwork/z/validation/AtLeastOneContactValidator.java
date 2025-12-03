package minhdoswe.socialnetwork.z.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import minhdoswe.socialnetwork.z.dto.request.RegisterRequest;


public class AtLeastOneContactValidator implements ConstraintValidator<AtLeastOneContact, RegisterRequest> {

    @Override
    public boolean isValid(RegisterRequest registerRequest, ConstraintValidatorContext constraintValidatorContext) {
        boolean hasEmail = registerRequest.getEmail() != null && !registerRequest.getEmail().isBlank();
        boolean hasPhoneNumber = registerRequest.getPhoneNumber() != null && !registerRequest.getPhoneNumber().isBlank();

        return hasEmail || hasPhoneNumber;
    }
}
