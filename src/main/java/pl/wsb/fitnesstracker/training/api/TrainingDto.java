package pl.wsb.fitnesstracker.training.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import pl.wsb.fitnesstracker.training.internal.ActivityType;

import pl.wsb.fitnesstracker.user.api.User;


import java.util.Date;

public record TrainingDto(User user, @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS+00:00") Date startTime,
                          @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS+00:00") Date endTime,
                          ActivityType activityType, double distance, double averageSpeed) {

}
