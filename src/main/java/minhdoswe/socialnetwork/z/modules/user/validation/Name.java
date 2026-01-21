package minhdoswe.socialnetwork.z.modules.user.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@NotBlank(message = "{validation.user.name.required}")
@Size(min = 2, max = 20, message = "{validation.user.name.length}")
public @interface Name {
    String message() default "{validation.user.name.format}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
