package dev.asteri.pasteva.controller;

import dev.asteri.pasteva.dto.UserProfileResponse;
import dev.asteri.pasteva.exception.UserIdNotFoundException;
import dev.asteri.pasteva.model.User;
import dev.asteri.pasteva.service.NoteService;
import dev.asteri.pasteva.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@Tag(name = "User", description = "User API")
@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final NoteService noteService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/profile")
    @Operation(
            summary = "Получение профиля"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь найден"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public UserProfileResponse getProfile(Authentication authentication) {
        log.info("Пользователь \"{}\": получение профиля.", authentication.getName());
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User profile = userService.findByUsername(userDetails.getUsername()).get();
        log.info("Пользователь \"{}\": профиль получен.", authentication.getName());
        return findById(profile.getId());
    }

    @GetMapping(value = "/public/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Operation(
            summary = "Получение профиля по ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь авторизирован"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public UserProfileResponse findById(@PathVariable UUID id) {
        log.info("Пользователь с ID \"{}\": поиск профиля.", id);
        var userOptional = userService.findById(id);
        if (userOptional.isEmpty()) {
            log.error("Пользователя с ID \"{}\" не существует!", id);
            throw new UserIdNotFoundException(id);
        }
        var user = userOptional.get();
        var userId = user.getId();
        var ids = noteService.findAllIdsByUserId(userId);
        log.info("Пользователь с ID \"{}\" найден.", id);
        return new UserProfileResponse(userId, user.getUsername(),
                user.getRole(), user.getRegisteredAt(),
                ids.size(), ids);
    }


    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/delete")
    @Operation(
            summary = "Удаление аккаунта"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь удалён"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "401", description = "Неавторизированный доступ")
    })
    public void deleteUser(Authentication authentication) {
        var name = authentication.getName();
        log.info("Пользователь \"{}\": удаление профиля.", name);
        var profile = getProfile(authentication);
        userService.deleteUser(profile.getId());
        log.info("Пользователь \"{}\": профиль удалён.", name);
    }
}
