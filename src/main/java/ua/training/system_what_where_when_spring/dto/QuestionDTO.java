package ua.training.system_what_where_when_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private Long id;
    private int number; // TODO decide to use it or not (if not question Id is used as #)
    private String nameWhoGotPointUa;
    private String nameWhoGotPointEn;
    private boolean isAppealPossible;
    private String appealStage;
}
