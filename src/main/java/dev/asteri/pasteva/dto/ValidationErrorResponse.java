package dev.asteri.pasteva.dto;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public record ValidationErrorResponse(
        HttpStatus status,
        String message,
        Map<String, String> fieldErrors
) {
    public ValidationErrorResponse(MethodArgumentNotValidException e) {
        this(
                HttpStatus.BAD_REQUEST,
                "Ошибка валидации",
                e.getBindingResult().getFieldErrors().stream()
                        .collect(Collectors.toMap(
                                FieldError::getField,
                                fieldError -> Optional.ofNullable(fieldError.getDefaultMessage())
                                        .orElse("Некорректное значение")
                        ))
        );
    }
}