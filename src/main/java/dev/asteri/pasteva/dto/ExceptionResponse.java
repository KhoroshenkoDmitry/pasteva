package dev.asteri.pasteva.dto;

import org.springframework.http.HttpStatus;


public record ExceptionResponse(
        HttpStatus status,
        String message
) {
}
