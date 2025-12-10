package minhdoswe.socialnetwork.z.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
