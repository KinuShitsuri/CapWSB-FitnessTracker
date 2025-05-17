package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserEmailDto;
import pl.wsb.fitnesstracker.user.api.UserSimpleDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

/**
 * The UserController class exposes RESTful endpoints for managing user data, including
 * operations such as retrieving, creating, updating, and deleting users. This controller
 * utilizes services and mappers to handle business logic and data transformation.
 *
 * Endpoints:
 * - GET /v1/users: Retrieves a list of all users.
 * - GET /v1/users/simple: Retrieves a simplified list of all users with limited information.
 * - GET /v1/users/{id}: Retrieves user details based on the provided user ID.
 * - GET /v1/users/email: Finds users by their email address, provided as a query parameter.
 * - GET /v1/users/older/{time}: Retrieves users older than a specific date.
 * - DELETE /v1/users/{userId}: Deletes a user by their unique ID.
 * - POST /v1/users: Adds a new user to the system.
 * - PUT /v1/users/{userId}: Updates the information of an existing user identified by the given ID.
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    /**
     * The service instance used to manage user-related operations within the controller.
     * It provides the business logic for handling user management functionality, including
     * retrieval, creation, updating, and deletion of user records. This is a dependency-injected
     * field utilized to delegate operations to the appropriate service layer implementation.
     */
    private final UserServiceImpl userService;

    /**
     * A mapper responsible for converting between {@code User} entities and their corresponding
     * Data Transfer Objects (DTOs). This component is used within the {@code UserController} to
     * transform entity objects into different representations, such as detailed, simplified,
     * or email-specific user information, and vice versa.
     *
     * The {@code UserMapper} separates concerns pertaining to data transformation, ensuring
     * that the business logic (handled by {@code UserServiceImpl}) remains decoupled from
     * the representation logic.
     */
    private final UserMapper userMapper;

    /**
     * Retrieves a list of all users. The method fetches user entities from the service layer,
     * maps them to their corresponding {@code UserDto} representations, and returns the result.
     *
     * @return a list of {@code UserDto} objects representing all users; if no users exist, returns an empty list
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Retrieves a list of all users in a simplified format. The simplified format
     * includes only basic user information, such as the user's ID, first name, and last name.
     *
     * @return a list of {@code UserSimpleDto} representing the simplified user data
     */
    @RequestMapping("/simple")
    @GetMapping
    public List<UserSimpleDto> getAllSimpleUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimpleDto)
                .toList();
    }

    /**
     * Retrieves user details based on the provided user ID by fetching the user
     * information from the service layer and converting it into a UserDto.
     *
     * @param id the unique identifier of the user to retrieve
     * @return the user's details as a UserDto
     * @throws NoSuchElementException if a user with the given ID is not found
     */
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUser(id)
                .stream()
                .map(userMapper::toDto)
                .findFirst().orElseThrow();
    }

    /**
     * Retrieves a list of users by their email address and maps the results to UserEmailDto objects.
     *
     * @param email the email address to search for; must not be null
     * @return a list of UserEmailDto objects matching the given email address;
     *         returns an empty list if no users are found
     */
    @GetMapping("/email")
    public List<UserEmailDto> getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email)
                .stream()
                .map(userMapper::toEmailDto)
                .toList();
    }

    /**
     * Retrieves a list of users who are older than the specified date.
     * This method maps the returned user entities to {@code UserDto} objects for response.
     *
     * @param time the date to compare the users' birth dates against; users older than this date will be included in the result
     * @return a list of {@code UserDto} objects representing users who are older than the specified date
     */
    @GetMapping("/older/{time}")
    public List<UserDto> getUserOlderThan(@PathVariable LocalDate time) {
        return userService.getUserOlderThan(time)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Deletes a user identified by their unique user ID.
     * This endpoint removes the user record from the system.
     *
     * @param userId the unique identifier of the user to be deleted
     * @return a {@code ResponseEntity} with no content indicating that the deletion was successful
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
         userService.deleteUserById(userId);
         return ResponseEntity.noContent().build();
    }

    /**
     * Adds a new user to the system. The method converts the provided {@code UserDto}
     * into a user entity, saves it, and then maps the saved entity back to a {@code UserDto}.
     * Returns the created user data along with an HTTP status of 201 (Created).
     *
     * @param userDto the data transfer object containing the information of the user to be added
     * @return a {@code ResponseEntity} containing the created user information as a {@code UserDto},
     *         along with an HTTP status of 201 (Created)
     * @throws InterruptedException if the operation is interrupted during processing
     */
    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) throws InterruptedException {
        UserDto createdUser = Stream.of(userDto)
                .map(userMapper::toEntity)
                .map(userService::createUser)
                .map(userMapper::toDto)
                .findFirst().orElseThrow();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdUser);
    }

    /**
     * Updates an existing user's information identified by their unique ID.
     * Converts the provided UserDto to a User entity, updates the user in the system,
     * and then maps the updated entity back to a UserDto for the response.
     *
     * @param userId the ID of the user to be updated
     * @param userDto the data transfer object containing the user's updated information
     * @return a {@code ResponseEntity} containing the updated user information as a {@code UserDto},
     *         along with an HTTP status of 201 (Created)
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId,
                                              @RequestBody UserDto userDto) {
        User userToUpdate = Stream.of(userDto).map(userMapper::toEntity).findAny().orElseThrow();
        UserDto updatedUser = Stream.of(userService.updateUser(userId, userToUpdate))
                .map(userMapper::toDto)
                .findFirst().orElseThrow();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(updatedUser);
    }

}