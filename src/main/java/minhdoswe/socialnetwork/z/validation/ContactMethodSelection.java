package minhdoswe.socialnetwork.z.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = ContactMethodSelectionValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ContactMethodSelection {

    String message() default "{validation.user.contact.exclusive}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
