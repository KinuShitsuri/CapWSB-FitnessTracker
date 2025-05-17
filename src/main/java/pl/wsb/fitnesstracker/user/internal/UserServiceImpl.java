package pl.wsb.fitnesstracker.user.internal;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserProvider;
import pl.wsb.fitnesstracker.user.api.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link UserService} and {@link UserProvider} interfaces, providing
 * business logic for user-related operations such as creation, retrieval, update, and deletion of users.
 * All operations are performed using the {@link UserRepository} instance.
 * This class also handles logging for user operations, exception handling for invalid inputs,
 * and encapsulates transactional logic.
 */
@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    /**
     * An instance of {@link UserRepository} used by {@link UserServiceImpl} to interact with the database
     * for performing CRUD operations and custom queries on {@link User} entities.
     *
     * This repository enables methods such as:
     * - Saving new or updated user entities.
     * - Querying users by criteria like email or birthdate.
     * - Deleting users by their unique identifiers.
     *
     * It provides essential persistence capabilities for implementing user-related business logic
     * within the service layer.
     */
    private final UserRepository userRepository;

    /**
     * Creates a new user in the system by persisting the provided user entity to the database.
     * If the user object already contains an ID, an exception is thrown to indicate
     * that the operation is not permitted for existing users.
     *
     * @param user the user entity to be created; must not have a pre-existing ID
     * @return the saved User object after successful persistence
     * @throws IllegalArgumentException if the provided user already has a non-null ID
     */
    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    /**
     * Updates the details of an existing user in the system using the given user ID and updated user data.
     * If the specified user ID does not exist, an EntityNotFoundException is thrown.
     * If no user ID is provided, an IllegalArgumentException is thrown.
     *
     * @param userId the ID of the user to be updated; must not be null
     * @param userToUpdate the user object containing updated field values;
     *                     any non-null field in this object will be used to update the corresponding field
     *                     in the existing user record
     * @return the updated User object after the changes have been saved to the database
     * @throws EntityNotFoundException if no user is found with the given ID
     * @throws IllegalArgumentException if the user ID is null
     */
    @Override
    public User updateUser(Long userId, User userToUpdate) {
        if (userId != null) {
            log.info("Updating User with id {}", userId);
            User updatedUser = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("Not found User with id: " + userId));
            Optional.ofNullable(userToUpdate.getBirthdate())
                    .ifPresent(updatedUser::setBirthdate);
            Optional.ofNullable(userToUpdate.getFirstName())
                    .ifPresent(updatedUser::setFirstName);
            Optional.ofNullable(userToUpdate.getEmail())
                    .ifPresent(updatedUser::setEmail);
            Optional.ofNullable(userToUpdate.getLastName())
                    .ifPresent(updatedUser::setLastName);
            return userRepository.save(updatedUser);
        }
        throw new IllegalArgumentException("User ID is empty!");
    }

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param userId the unique identifier of the user to retrieve; must not be null
     * @return an {@code Optional} containing the user if found, or an empty {@code Optional} if no user exists with the given ID
     */
    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Retrieves a list of users that match the given email address.
     *
     * @param email the email address to search for; must not be null
     * @return a list of {@link User} objects that match the given email address;
     *         returns an empty list if no users are found
     */
    @Override
    public List<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Retrieves a list of users whose birthdate is earlier than the specified date.
     *
     * @param birthdate the date used as a filter to find users older than the specified date
     * @return a list of users whose birthdate is before the specified date
     */
    @Override
    public List<User> getUserOlderThan(final LocalDate birthdate){
        return userRepository.findByBirthdateBefore(birthdate);
    }

    /**
     * Retrieves all user records from the database.
     *
     * @return a list of all users stored in the system; if no users are found, returns an empty list
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Deletes the user associated with the specified unique identifier from the system's data repository.
     *
     * @param id the unique identifier of the user to be deleted; must not be null
     */
    @Override
    public void deleteUserById(final Long id){
        userRepository.deleteById(id);
    }

}