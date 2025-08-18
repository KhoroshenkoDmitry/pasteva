package dev.asteri.pasteva.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

public class NoteIdNotFoundException extends ResponseStatusException {

  static private final String pattern = "Запись с ID %s не найдена";
  public NoteIdNotFoundException(UUID id) {
    super(HttpStatus.NOT_FOUND, String.format(pattern, id.toString()));
  }

  public NoteIdNotFoundException(UUID id, Throwable cause) {
    super(HttpStatus.NOT_FOUND, String.format(pattern, id.toString()), cause);
  }

  protected NoteIdNotFoundException(UUID id, Throwable cause, String messageDetailCode, Object[] messageDetailArguments) {
    super(HttpStatus.NOT_FOUND, String.format(pattern, id.toString()), cause, messageDetailCode, messageDetailArguments);
  }

}
