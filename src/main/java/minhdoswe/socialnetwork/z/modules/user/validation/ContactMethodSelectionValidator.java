package minhdoswe.socialnetwork.z.modules.user.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import minhdoswe.socialnetwork.z.modules.user.model.dto.auth.RegisterRequest;


public class ContactMethodSelectionValidator implements ConstraintValidator<ContactMethodSelection, RegisterRequest> {

    @Override
    public boolean isValid(
            RegisterRequest registerRequest,
            ConstraintValidatorContext constraintValidatorContext) {

        boolean hasEmail = registerRequest.getEmail() != null;
        boolean hasPhoneNumber = registerRequest.getPhoneNumber() != null;

        return hasEmail || hasPhoneNumber;
    }
}
