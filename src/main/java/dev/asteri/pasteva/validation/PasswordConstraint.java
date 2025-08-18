package dev.asteri.pasteva.validation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Schema(description = "Требования: латиница, цифры, ._- без пробелов")
public @interface PasswordConstraint {
    String message() default "Некорректный пароль: используйте только латинские буквы," +
            "цифры и специальные символы, от 8 символов";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

