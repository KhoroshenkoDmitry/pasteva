package dev.asteri.pasteva.controller;

import dev.asteri.pasteva.dto.JwtAuthenticationResponse;
import dev.asteri.pasteva.dto.SignInRequest;
import dev.asteri.pasteva.dto.SignUpRequest;
import dev.asteri.pasteva.service.AuthenticationService;
import dev.asteri.pasteva.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Authentication API")
@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final UserService userService;

    @PostMapping("/signup")
    @Operation(
            summary = "Регистрация пользователя"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Имя пользователя занято или проблема с логином/паролем")
    })
    public ResponseEntity<String> register(
            @Valid @RequestBody SignUpRequest registrationDto) {

        if (userService.existsByUsername(registrationDto.getUsername())) {
            return ResponseEntity.badRequest().body("Имя пользователя уже занято");
        }

        authenticationService.register(registrationDto);

        return ResponseEntity.ok("Регистрация прошла успешно");
    }

    @Operation(
            summary = "Авторизация пользователя"
    )
    @PostMapping("/signin")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь авторизирован"),
            @ApiResponse(responseCode = "401", description = "Пользователь не найден")
    })
    public ResponseEntity<JwtAuthenticationResponse> authenticate(
            @Valid @RequestBody SignInRequest request) {

        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @Operation(
            summary = "Обновление токена"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Токен обновлен"),
    })
    @PostMapping("/refresh_token")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {

        return authenticationService.refreshToken(request, response);
    }
}