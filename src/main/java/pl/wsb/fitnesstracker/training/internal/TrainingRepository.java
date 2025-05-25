package pl.wsb.fitnesstracker.training.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsb.fitnesstracker.training.api.Training;

import java.util.List;

interface TrainingRepository extends JpaRepository<Training, Long> {
    default List<Training> findByUserId(final long userId) {
        return findAll().stream()
                .filter(training -> training.getUser().getId() != null)
                .toList();
    }
}
