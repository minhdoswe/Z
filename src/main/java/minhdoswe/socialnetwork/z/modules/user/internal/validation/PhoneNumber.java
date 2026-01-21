package minhdoswe.socialnetwork.z.modules.user.internal.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

@Documented
@Target({ ElementType.FIELD })
@Constraint(validatedBy = PhoneNumberValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Size(min = 10, max = 11, message = "{validation.user.phone.length}")
public @interface PhoneNumber {
    String message() default "{validation.user.phone.format}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
