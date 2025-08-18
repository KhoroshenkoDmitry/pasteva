package dev.asteri.pasteva.validation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Schema(description = "Требования: латиница, цифры, ._- без пробелов")
public @interface UsernameConstraint {
    String message() default "Некорректное имя пользователя: используйте латинские символы и цифры" +
            "от 3 до 50 символов";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}