package validation.util.validation.phone;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

@Target({METHOD, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PhoneConstraintValidator.class)
public @interface PhoneConstraint {
    String message() default "Должно быть в формате +<код страны><номер внутри страны>";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
