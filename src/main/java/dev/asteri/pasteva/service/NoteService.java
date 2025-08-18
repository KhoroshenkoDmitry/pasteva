package dev.asteri.pasteva.service;

import dev.asteri.pasteva.model.Note;
import dev.asteri.pasteva.repository.NoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Primary
@Service
@AllArgsConstructor
public class NoteService {
    private final NoteRepository repository;

    public Note saveNote(Note note) {
        return repository.save(note);
    }

    public Optional<Note> findById(UUID id) {
        return repository.findById(id);
    }

    public void deleteNote(UUID id) {
        repository.deleteById(id);
    }

    public void updateBodyById(String body, UUID noteId) {
        repository.updateBodyById(body, noteId);
    }

    public void updateDescriptionById(String description, UUID noteId) {
        repository.updateDescriptionById(description, noteId);
    }

    public void updateNameById(String name, UUID noteId) {
        repository.updateNameById(name, noteId);
    }

    public boolean existsById(UUID noteId) {
        return repository.existsById(noteId);
    }

    public List<UUID> findAllIdsByUserId(UUID userId) {
        return repository.findAllIdsByUserId(userId);
    }
}
