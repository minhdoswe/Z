package minhdoswe.socialnetwork.z.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Constraint(validatedBy = PhoneNumberValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@NotBlank
@Size(min = 10, max = 11, message = )
public @interface PhoneNumber {
    String message() default "{validation.user.phone.format}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
