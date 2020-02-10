package ua.training.game.service;

import org.springframework.stereotype.Service;
import ua.training.game.domain.Appeal;
import ua.training.game.domain.AppealedQuestion;
import ua.training.game.domain.Question;

@Service
public class AppealedQuestionService {

    public AppealedQuestion toAppealedQuestion(Question question, Appeal appeal) {
        return AppealedQuestion.builder()
                .question(question)
                .appeal(appeal)
                .build();
    }
}