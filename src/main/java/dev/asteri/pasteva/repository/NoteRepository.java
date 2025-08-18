package dev.asteri.pasteva.repository;

import dev.asteri.pasteva.model.Note;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, UUID> {

    @Modifying
    @Query(value = "UPDATE notes SET body = :body WHERE note_id = :noteId",
            nativeQuery = true)
    @Transactional
    void updateBodyById(@Param("body") @NotBlank String body, @Param("noteId") @NotNull UUID noteId);

    @Modifying
    @Query(value = "UPDATE notes SET description = :description WHERE note_id = :noteId",
            nativeQuery = true)
    void updateDescriptionById(@Param("description") @NotBlank String description, @Param("noteId") @NotNull UUID noteId);

    @Modifying
    @Query(value = "UPDATE notes SET note_name = :name WHERE note_id = :noteId",
            nativeQuery = true)
    @Transactional
    void updateNameById(@Param("name") @NotBlank String name, @Param("noteId") @NotNull UUID noteId);

    @Query(value = "SELECT note_id FROM notes WHERE user_id = :userId", nativeQuery = true)
    List<UUID> findAllIdsByUserId(@Param("userId") @NotNull UUID userId);


}
