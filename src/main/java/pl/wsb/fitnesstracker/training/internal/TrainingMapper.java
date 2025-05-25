package pl.wsb.fitnesstracker.training.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.user.api.User;

@Component
public class TrainingMapper {
    TrainingDto toDto(Training training) {
        return new TrainingDto(training.getUser(),training.getStartTime(),training.getEndTime()
        ,training.getActivityType(),training.getDistance(),training.getAverageSpeed());
    }
}
