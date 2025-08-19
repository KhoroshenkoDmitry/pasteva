package dev.asteri.pasteva.advice;

import dev.asteri.pasteva.dto.ExceptionResponse;
import dev.asteri.pasteva.exception.NoteIdNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NoteAdvice {

    @ExceptionHandler(NoteIdNotFoundException.class)
    @Operation(hidden = true)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionResponse> handleException(NoteIdNotFoundException e) {
        var response = new ExceptionResponse(HttpStatus.NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(response, response.status());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @Operation(hidden = true)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ExceptionResponse> handleException(AccessDeniedException e) {
        var response = new ExceptionResponse(HttpStatus.FORBIDDEN, "Проблемы с авторизацией: " + e.getMessage());
        return new ResponseEntity<>(response, response.status());
    }

}