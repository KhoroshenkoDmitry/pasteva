package dev.asteri.pasteva.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на создание записи")
public class CreateNoteRequest {
    @Schema(description = "Имя записи", example = "Bug report")
    @Size(max = 50, message = "Имя записи должно содержать до 50 символов")
    @NotBlank(message = "Имя записи не может быть пустым")
    private String name;

    @Schema(description = "Описание", example = "Bug report на приложение ExampleApp")
    @Size(max = 255, message = "Описание должно содержать до 255 символов")
    private String description;

    @Schema(description = "Содержание", example = "Когда я нажал на кнопку, то у меня взорвался компьютер. Вот логи:...")
    @NotBlank(message = "Содержание не может быть пустым")
    private String body;
}
