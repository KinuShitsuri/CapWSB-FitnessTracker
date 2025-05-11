package pl.wsb.fitnesstracker.user.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;

public record UserSimpleDto(@Nullable Long id, String firstName, String lastName){

}
