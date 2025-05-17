package pl.wsb.fitnesstracker.user.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserEmailDto;
import pl.wsb.fitnesstracker.user.api.UserSimpleDto;

/**
 * The UserMapper class is responsible for transforming data between different representations
 * of user-related entities, such as converting between entity models and DTOs.
 *
 * This class provides methods for mapping:
 * - User entity to UserDto.
 * - User entity to UserSimpleDto with basic user information.
 * - User entity to UserEmailDto with email-related user details.
 * - UserDto back to User entity.
 *
 * It ensures the separation of concerns between persistence and API representations
 * while facilitating the data transformation required for various operations.
 */
@Component
class UserMapper {

    /**
     * Converts a User entity object to a UserDto object.
     *
     * @param user the User entity to be converted into a UserDto. Must not be null.
     * @return a UserDto object containing the corresponding data from the provided User entity.
     */
    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }

    /**
     * Converts a {@link User} entity into a {@link UserSimpleDto}, which contains
     * only the basic user information such as ID, first name, and last name.
     *
     * @param user the {@link User} entity to be converted. Must not be null.
     * @return a {@link UserSimpleDto} containing the user's ID, first name, and last name.
     */
    UserSimpleDto toSimpleDto(User user) {
        return new UserSimpleDto(user.getId(),
                user.getFirstName(),
                user.getLastName());
    }

    /**
     * Maps a User entity to a UserEmailDto, extracting the user's ID and email.
     *
     * @param user the user entity containing the ID and email to be mapped
     * @return a UserEmailDto containing the ID and email of the provided user
     */
    UserEmailDto toEmailDto(User user) {
        return new UserEmailDto(user.getId(),
                user.getEmail());
    }

    /**
     * Converts a UserDto object into a User entity.
     *
     * @param userDto the UserDto object containing the user details to be mapped
     * @return a User entity initialized with the data from the provided UserDto
     */
    User toEntity(UserDto userDto) {
        return new User(
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email());
    }

}
