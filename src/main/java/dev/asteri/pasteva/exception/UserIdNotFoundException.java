package dev.asteri.pasteva.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;


public class UserIdNotFoundException extends ResponseStatusException {

    static private final String pattern = "Пользователь с ID %s не найден";
    public UserIdNotFoundException(UUID id) {
        super(HttpStatus.NOT_FOUND, String.format(pattern, id.toString()));
    }

    public UserIdNotFoundException(UUID id, Throwable cause) {
        super(HttpStatus.NOT_FOUND, String.format(pattern, id.toString()), cause);
    }

    protected UserIdNotFoundException(UUID id, Throwable cause, String messageDetailCode, Object[] messageDetailArguments) {
        super(HttpStatus.NOT_FOUND, String.format(pattern, id.toString()), cause, messageDetailCode, messageDetailArguments);
    }

}
