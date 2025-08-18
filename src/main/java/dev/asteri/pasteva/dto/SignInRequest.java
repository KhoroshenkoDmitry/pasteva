package dev.asteri.pasteva.dto;

import dev.asteri.pasteva.validation.PasswordConstraint;
import dev.asteri.pasteva.validation.UsernameConstraint;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Запрос на авторизацию")
public class SignInRequest {
    @Schema(description = "Имя пользователя", example = "Asteri",
            allOf = {UsernameConstraint.class})
    @NotBlank(message = "Имя пользователя не может быть пустым")
    @UsernameConstraint
    private String username;

    @Schema(description = "Пароль", example = "C0olP4sswrd",
            allOf = {PasswordConstraint.class})
    @NotBlank(message = "Пароль не может быть пустым")
    @PasswordConstraint
    private String password;
}
