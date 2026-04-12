package jotalac.market_viewer.market_viewer_api.validation_annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidTimezoneValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTimezone {
    String message() default "Invalid timezone";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};
}
