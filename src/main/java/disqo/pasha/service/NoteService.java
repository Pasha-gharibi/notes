package disqo.pasha.service;

import disqo.pasha.domain.Note;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Note}.
 */
public interface NoteService {

    /**
     * Save a note.
     *
     * @param note the entity to save.
     * @return the persisted entity.
     */
    Note save(Note note);

    /**
     * Get all the notes.
     *
     * @return the list of entities.
     */
    List<Note> findAll();


    /**
     * Get the "id" note.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Note> findOne(Long id);

    /**
     * Delete the "id" note.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
