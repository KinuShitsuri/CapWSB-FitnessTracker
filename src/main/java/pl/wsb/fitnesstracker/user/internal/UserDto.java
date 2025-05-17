package pl.wsb.fitnesstracker.user.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

/**
 * A data transfer object (DTO) that represents a user.
 * This class encapsulates the user's details for communication between different layers
 * within the application or with external services.
 *
 * The UserDto record contains the following attributes:
 * - An optional unique identifier for the user (id).
 * - The first name of the user (firstName).
 * - The last name of the user (lastName).
 * - The user's date of birth (birthdate), formatted as "yyyy-MM-dd".
 * - The user's email address (email).
 *
 * The purpose of this class is to provide a simple, immutable representation of a user's data
 * and to ensure consistency when transferring user information.
 *
 * Note: The birthdate is formatted using {@code @JsonFormat} for serializing and deserializing
 * JSON data with the specified date pattern.
 */
record UserDto(@Nullable Long id, String firstName, String lastName,
               @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
               String email) {

}
