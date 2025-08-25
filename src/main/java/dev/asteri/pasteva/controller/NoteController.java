package dev.asteri.pasteva.controller;

import dev.asteri.pasteva.dto.CreateNoteRequest;
import dev.asteri.pasteva.dto.CreateNoteResponse;
import dev.asteri.pasteva.dto.UpdateNoteRequest;
import dev.asteri.pasteva.exception.NoteIdNotFoundException;
import dev.asteri.pasteva.model.Note;
import dev.asteri.pasteva.model.User;
import dev.asteri.pasteva.service.NoteService;
import dev.asteri.pasteva.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.UUID;

@Slf4j
@Tag(name = "Note", description = "Note API")
@RestController
@RequestMapping("/v1/notes")
@AllArgsConstructor
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    @PostMapping(value = "/public/save",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @Operation(
            summary = "Сохранение записи",
            description = "Сохранение записи, возможно, анонимное"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Запись успешно создана"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    })
    public CreateNoteResponse saveNote(@Valid @RequestBody CreateNoteRequest request) {
        var name = request.getName();
        log.info("Запись: \"{}\", начато создание записи.", name);
        var note = new Note();
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            User user = userService.findByUsername(username).get();
            note.setUser(user);
            log.info("Запись: \"{}\", установлен автор: \"{}\".", name, username);
        }
        note.setNoteName(name);
        note.setBody(request.getBody());
        var description = request.getDescription();
        if (description != null) {
            note.setDescription(description);
        }
        var savedNote = noteService.saveNote(note);
        log.info("Запись \"{}\" сохранена.", name);
        if (savedNote.getUser() == null) {
            return new CreateNoteResponse(savedNote.getId(), savedNote.getCreatedAt(),
                    savedNote.getNoteName());
        }
        return new CreateNoteResponse(savedNote.getId(), savedNote.getCreatedAt(),
                savedNote.getNoteName(), savedNote.getUser().getUsername());
    }

    @GetMapping(value = "/public/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Operation(
            summary = "Нахождение записи по ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запись найдена"),
            @ApiResponse(responseCode = "404", description = "Запись не найдена")
    })
    public Note findById(@PathVariable UUID id) {
        log.info("Запись с ID \"{}\". Начат поиск.", id);
        var note = noteService.findById(id);
        if (note.isEmpty()) {
            log.error("Записи с ID \"{}\" не существует.", id);
            throw new NoteIdNotFoundException(id);
        }
        log.info("Запись с ID \"{}\" найдена.", id);
        return note.get();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/update")
    @Operation(
            summary = "Обновление записи",
            description = "Обновление записи, требуется аккаунт"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запись обновлена"),
            @ApiResponse(responseCode = "404", description = "Запись не найдена"),
            @ApiResponse(responseCode = "403", description = "Неавторизированный доступ"),
    })
    public void updateNote(@Valid @RequestBody UpdateNoteRequest request) throws InvalidParameterException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        var id = request.getId();
        var note = findById(id);
        var user = note.getUser();
        log.info("Запись с ID \"{}\" обновляется.", id);
        if (user == null) {
            log.error("Пользователь не авторизован!");
            throw new AccessDeniedException("Пользователь не авторизован!");
        }
        if (!username.equals(user.getUsername())) {
            log.error("Запись с ID \"{}\" не принадлежит пользователю \"{}\"!", id, user.getId());
            throw new AccessDeniedException("Это не Ваша запись!");
        }
        var name = request.getName();
        if (name != null) {
            log.info("Запись с ID \"{}\": имя изменено с \"{}\" на \"{}\"", id, note.getNoteName(), name);
            noteService.updateNameById(name, id);
        }
        var description = request.getDescription();
        if (description != null) {
            log.info("Запись с ID \"{}\": описание изменено с \"{}\" на \"{}\"", id, note.getDescription(), description);
            noteService.updateDescriptionById(description, id);
        }
        var body = request.getBody();
        if (body != null) {
            log.info("Запись с ID \"{}\": тело изменено", id);
            noteService.updateBodyById(body, id);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление записи",
            description = "Удаление записи, требуется аккаунт"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запись удалена"),
            @ApiResponse(responseCode = "404", description = "Запись не найдена"),
            @ApiResponse(responseCode = "403", description = "Неавторизированный доступ"),
    })
    public void deleteNote(@PathVariable UUID id) {
        log.info("Запись с ID \"{}\": начато удаление.", id);
        var noteOptional = noteService.findById(id);
        if (noteOptional.isEmpty()) {
            log.error("Запись с ID \"{}\" не существует!", id);
            throw new NoteIdNotFoundException(id);
        }
        var note = noteOptional.get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        var user = userService.findByUsername(username).get();
        if (!user.getId().equals(note.getUser().getId())) {
            log.error("Запись с ID \"{}\" не принадлежит пользователю {}!", id, username);
            throw new AccessDeniedException("Это не Ваша запись!");
        }
        noteService.deleteNote(id);
        log.info("Запись с ID \"{}\" успешно удалена!", id);
    }
}
