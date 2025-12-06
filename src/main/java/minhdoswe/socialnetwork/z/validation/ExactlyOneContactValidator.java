package minhdoswe.socialnetwork.z.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import minhdoswe.socialnetwork.z.dto.request.RegisterRequest;


public class ExactlyOneContactValidator implements ConstraintValidator<ExactlyOneContact, RegisterRequest> {

    @Override
    public boolean isValid(RegisterRequest registerRequest, ConstraintValidatorContext constraintValidatorContext) {
        boolean hasEmail = registerRequest.getEmail() != null;
        boolean hasPhoneNumber = registerRequest.getPhoneNumber() != null;

        return hasEmail ^ hasPhoneNumber;
    }
}
