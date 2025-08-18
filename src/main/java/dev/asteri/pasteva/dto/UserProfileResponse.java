package dev.asteri.pasteva.dto;

import dev.asteri.pasteva.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ с профилем пользователя")
public class UserProfileResponse {
    @Schema(description = "ID пользователя", example = "6f7aa557-52e5-43a8-9a1f...")
    private UUID id;

    @Schema(description = "Имя", example = "Asteri")
    private String username;

    @Schema(description = "Роль", example = "ROLE_USER")
    private Role role;

    @Schema(description = "Зарегистрирован", example = "2025-08-16T08:32:07.263133Z")
    private Instant registeredAt;

    @Schema(description = "Количество записей", example = "25")
    private int notesCount;

    @Schema(description = "Список ID записей")
    private List<UUID> notes;
}