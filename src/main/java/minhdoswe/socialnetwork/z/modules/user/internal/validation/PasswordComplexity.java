package minhdoswe.socialnetwork.z.modules.user.internal.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordComplexityValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Size(min = 6, max = 50, message = "{validation.user.password.length}")
@NotBlank(message = "{validation.usesr.password.required}")
public @interface PasswordComplexity {
    String message() default "{validation.user.password.complexity}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
