package ua.training.game.web.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(of = "id")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {
    private Long id;
    private LocalDate date;
    private String firstPlayerNameUa;
    private String firstPlayerNameEn;
    private String secondPlayerNameUa;
    private String secondPlayerNameEn;
    private String scores;
    private String appealStage;
    private boolean isAppealPossible;
    private List<QuestionDTO> questionDTOs = new ArrayList<>();
}
