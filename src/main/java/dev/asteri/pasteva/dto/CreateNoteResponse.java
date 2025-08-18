package dev.asteri.pasteva.dto;

import dev.asteri.pasteva.validation.UsernameConstraint;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ с информацией про запись")
public class CreateNoteResponse {
    @Schema(description = "ID записи", example = "6f7aa557-52e5-43a8-9a1f...")
    private UUID id;

    @Schema(description = "Время создания", example = "2025-08-16T08:32:07.263133Z")
    private Instant createdAt;

    @Schema(description = "Имя записи", example = "Note example")
    @Size(min = 1, max = 60, message = "Имя записи должно содержать от 1 до 60 символов")
    private String name;

    @Schema(description = "Имя пользователя", example = "Asteri")
    @Size(min = 5, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @UsernameConstraint
    private String username;

    public CreateNoteResponse(UUID id, Instant createdAt, @NotNull String noteName) {
        this(id, createdAt, noteName, null);
    }
}