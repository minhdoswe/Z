package minhdoswe.socialnetwork.z.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import minhdoswe.socialnetwork.z.dto.request.auth.RegisterRequest;


public class ContactMethodSelectionValidator implements ConstraintValidator<ContactMethodSelection, RegisterRequest> {

    @Override
    public boolean isValid(
            RegisterRequest registerRequest,
            ConstraintValidatorContext constraintValidatorContext) {

        boolean isValid = registerRequest.getEmail() != null || registerRequest.getPhoneNumber() != null;

        if (!isValid) {
            if (registerRequest.getEmail())
        }


        boolean hasEmail = registerRequest.getEmail() != null;
        boolean hasPhoneNumber = registerRequest.getPhoneNumber() != null;

        return hasEmail || hasPhoneNumber;
    }
}
