package pl.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService {

    User createUser(User user);

    /**
     * Updates the existing user specified by the provided ID with new information.
     * If the user with the given ID does not exist, an exception may be thrown by the implementation.
     *
     * @param id   the unique identifier of the user to be updated
     * @param user the updated user information to be applied
     * @return the updated {@link User} object after successful application of changes
     */
    User updateUser(Long id, User user);

    /**
     * Retrieves a list of users who are older than the given birthdate.
     *
     * @param birthdate the birthdate used as the threshold for determining users' ages
     * @return a list of {@link User} objects corresponding to users who are older than the specified date
     */
    List<User> getUserOlderThan(final LocalDate birthdate);

    /**
     * Deletes a user identified by the provided ID.
     * If the user with the specified ID does not exist, an exception may be thrown by the implementation.
     *
     * @param id the unique identifier of the user to be deleted
     */
    void deleteUserById(final Long id);

}
