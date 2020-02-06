package ua.training.system_what_where_when_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private Long id;
    private int number; // TODO check where it is used
    private String nameWhoGotPointUa;
    private String nameWhoGotPointEn;
    private boolean isAppealPossible;
    private String appealStage;
}
