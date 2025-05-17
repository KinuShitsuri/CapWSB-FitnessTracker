package pl.wsb.fitnesstracker.user.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities. Extends Spring Data JPA's {@link JpaRepository},
 * providing default methods for CRUD operations and additional custom query methods.
 *
 * This interface defines custom methods for retrieving users based on specific criteria.
 */
interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default List<User> findByEmail(String email) {

        String lowerInput = email.toLowerCase();

        return findAll().stream()
                .filter(user -> user.getEmail() != null && user.getEmail().toLowerCase().contains(lowerInput))
                .toList();
    }

    /**
     * Finds and retrieves a list of users whose birthdate is before the specified date.
     *
     * @param birthdate the date used as an upper limit filter for user birthdates
     * @return a list of users whose birthdate is before the specified date
     */
    default List<User> findByBirthdateBefore(LocalDate birthdate) {
        return findAll().stream().toList();
    }

}
