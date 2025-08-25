package dev.asteri.pasteva.advice;

import dev.asteri.pasteva.dto.ExceptionResponse;
import dev.asteri.pasteva.exception.UserIdNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class UserAdvice {

    @ExceptionHandler({UserIdNotFoundException.class,
            UsernameNotFoundException.class})
    @Operation(hidden = true)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionResponse> handleException(UserIdNotFoundException e) {
        var response = new ExceptionResponse(HttpStatus.NOT_FOUND, e.getMessage());
        log.error("Статус: {}\nОшибка: {}\nПричина: {}\nТело: {}\n", HttpStatus.NOT_FOUND, e.getMessage(), e.getCause(), e.getBody());
        return new ResponseEntity<>(response, response.status());
    }

}