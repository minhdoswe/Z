package minhdoswe.socialnetwork.z.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = AtLeastOneContactValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExactlyOneContact {

    String message() default "You must provide either an email or a phone number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
