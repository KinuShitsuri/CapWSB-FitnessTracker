package pl.wsb.fitnesstracker.user.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing a user in the system.
 * This class is designed to encapsulate the relevant data for transferring user information
 * between different layers or components in the application.
 *
 * Features:
 * - Encapsulates user-related data, including ID, first name, last name, birthdate, and email address.
 * - Provides read-only access to its data after instantiation, as it is implemented using a Java record.
 *
 * Fields:
 * - `id`: A nullable identifier that uniquely represents a user.
 * - `firstName`: The first name of the user. This value is required.
 * - `lastName`: The last name of the user. This value is required.
 * - `birthdate`: The user's birthdate formatted as `yyyy-MM-dd` using the `@JsonFormat` annotation.
 * - `email`: The user's email address. This value is required.
 *
 * Annotations:
 * - `@Nullable`: Specifies that the `id` field may have a null value.
 * - `@JsonFormat`: Configures the format of the `birthdate` field for serialization and deserialization.
 */
public record UserDto(@Nullable Long id, String firstName, String lastName,
                      @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
                      String email) {

}
