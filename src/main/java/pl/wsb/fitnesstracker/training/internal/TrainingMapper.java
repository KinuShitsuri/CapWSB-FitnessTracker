package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.training.api.SimpleTrainingDto;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.internal.UserServiceImpl;

@Component
@RequiredArgsConstructor
public class TrainingMapper {

    private final UserServiceImpl userService;

    TrainingDto toDto(Training training) {
        return new TrainingDto(training.getUser(),training.getStartTime(),training.getEndTime()
        ,training.getActivityType(),training.getDistance(),training.getAverageSpeed());
    }

    Training simpleToEntity(SimpleTrainingDto simpleTrainingDto) {
        return new Training(userService.getUser(simpleTrainingDto.userId()).orElseThrow(), simpleTrainingDto.startTime(),
                simpleTrainingDto.endTime(), simpleTrainingDto.activityType(),
                simpleTrainingDto.distance(), simpleTrainingDto.averageSpeed());
    }
}
