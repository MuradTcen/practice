package validation.util.validation.cyrillic;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

@Target({METHOD, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CyrillicConstraintValidator.class)
public @interface CyrillicConstraint {
    String message() default "Only cyrillic, spaces and digits";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
