package dev.asteri.pasteva.advice;

import dev.asteri.pasteva.dto.ValidationErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ValidationAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @Operation(hidden = true)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleValidationException(
            MethodArgumentNotValidException e
    ) {
        log.error("Ошибка: {}\nПричина: {}\nТело: {}\n", e.getMessage(), e.getCause(), e.getBody());
        return new ValidationErrorResponse(e);
    }

}
