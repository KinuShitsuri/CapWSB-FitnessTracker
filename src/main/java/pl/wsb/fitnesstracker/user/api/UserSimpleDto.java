package pl.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;

/**
 * A Data Transfer Object (DTO) for representing simplified user information.
 * This class is implemented as a Java record, making it an immutable data holder for efficient data transfer.
 *
 * Purpose:
 * - Encapsulates minimal user-related data, specifically the user ID, first name, and last name.
 * - Designed to be used in scenarios where only basic user information is required,
 *   such as displaying a user summary or a lightweight response format.
 *
 * Features:
 * - Immutable by default due to its record structure.
 * - Provides concise and predictable representation of user identity data.
 * - Includes nullable handling for the ID field to indicate a case where the user is not yet persistently stored.
 *
 * Fields:
 * - `id`: A nullable unique identifier for a user, useful when referencing a persisted user entity.
 * - `firstName`: The first name of the user, non-nullable and mandatory.
 * - `lastName`: The last name of the user, non-nullable and mandatory.
 *
 * Annotations:
 * - `@Nullable`: Indicates that the `id` field may have a null value.
 */
public record UserSimpleDto(@Nullable Long id, String firstName, String lastName){

}
