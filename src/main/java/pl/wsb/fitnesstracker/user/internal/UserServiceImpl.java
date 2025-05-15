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

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

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

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public List<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getUserOlderThan(final LocalDate birthdate){
        return userRepository.findByBirthdateBefore(birthdate);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(final Long id){
        userRepository.deleteById(id);
    }

}