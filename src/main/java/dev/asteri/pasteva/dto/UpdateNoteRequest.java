package dev.asteri.pasteva.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Запрос на обновление записи")
public class UpdateNoteRequest {

    @Schema(description = "ID записи", example = "6f7aa557-52e5-43a8-9a1f...")
    @NotNull(message = "ID записи не может быть пустым")
    private UUID id;

    @Schema(description = "Обновленное имя записи", example = "New name")
    @Size(max = 50, message = "Имя записи должно содержать до 50 символов")
    private String name;

    @Schema(description = "Обновленное описание записи", example = "New description")
    @Size(max = 255, message = "Описание должно содержать до 255 символов")
    private String description;

    @Schema(description = "Обновленное тело записи", example = "New body")
    private String body;

    @AssertTrue(message = "Нужно обновить хотя бы одно поле!")
    private boolean isValid() {
        return (name != null ||
                description != null ||
                body != null);
    }
}
