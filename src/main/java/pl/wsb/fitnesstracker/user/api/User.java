package pl.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Entity class representing a user in the system.
 * This class is mapped to the "users" table in the database and represents user-related information such as name, birthdate, and email.
 * It is designed for persistence and manipulation using JPA.
 *
 * An instance of this class is immutable except for fields that are explicitly annotated for JPA updates.
 *
 * Features and behavior:
 * - A unique identifier (ID) is auto-generated for each user record.
 * - Contains basic user information like first name, last name, birthdate, and email.
 * - The `email` field is unique and mandatory for identifying users.
 * - Provides a constructor for initializing new user objects with the required fields.
 *
 * Annotations used:
 * - `@Entity`: Specifies that this class is a JPA entity.
 * - `@Table`: Defines the table name in the database for mapping this entity.
 * - `@Getter` and `@Setter`: Automatically generates getter and setter methods for fields.
 * - `@NoArgsConstructor`: Generates a no-argument constructor with protected access level (for JPA).
 * - `@ToString`: Automatically generates a `toString` method for easy debugging and logging.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false, unique = true)
    private String email;

    public User(
            final String firstName,
            final String lastName,
            final LocalDate birthdate,
            final String email) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
    }

}

