package ua.training.game.web.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class NewGameFormDataDTO {
    @NotBlank
    private String firstPlayerId;
    @NotBlank
    private String secondPlayerId;
    @Pattern(regexp = "^[0-9]*$", message = "validation.max.scores.pattern")
    @Max(value = 10, message = "validation.maxScores.exceeds.max") //TODO move max to properties
    private String maxScores;

}

