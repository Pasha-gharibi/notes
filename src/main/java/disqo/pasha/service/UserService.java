package disqo.pasha.service;

import disqo.pasha.domain.User;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link User}.
 */
public interface UserService {

    /**
     * Save a user.
     *
     * @param user the entity to save.
     * @return the persisted entity.
     */
    User save(User user);

    /**
     * Get all the users.
     *
     * @return the list of entities.
     */
    List<User> findAll();


    /**
     * Get the "id" user.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<User> findOne(Long id);

    /**
     * Delete the "id" user.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get the "email" user.
     *
     * @param email the id of the entity.
     */
    Optional<User> findByEmail(String email);
}
