package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wsb.fitnesstracker.training.api.SimpleTrainingDto;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.training.api.TrainingProvider;

import java.util.List;
import java.util.stream.Stream;


@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
class TrainingController {

    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;

    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.getAllTrainings()
                .stream().map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/{userId}")
    public List<TrainingDto> getAllTrainingsByUserId(@PathVariable("userId") Long userId) {
        return trainingService.getAllTrainingsByUserId(userId)
                .stream().map(trainingMapper::toDto)
                .toList();
    }

    @PostMapping
    public ResponseEntity<TrainingDto> createTraining(@RequestBody SimpleTrainingDto simpleTrainingDto) {
        Training createdTraining = Stream.of(simpleTrainingDto)
                .map(trainingMapper::simpleToEntity)
                .map(trainingService::createTraining)
                .findFirst().orElseThrow();

        TrainingDto trainingDto = trainingMapper.toDto(createdTraining);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(trainingDto);
    }
}