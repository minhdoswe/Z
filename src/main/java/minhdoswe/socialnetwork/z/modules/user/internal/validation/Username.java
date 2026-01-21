package minhdoswe.socialnetwork.z.modules.user.internal.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

@Documented
@NotBlank(message = "{validation.user.username.required}")
@Size(min = 2, max = 20, message = "{validation.user.username.length}")
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Username {
    String message() default "{validation.user.username.format}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
