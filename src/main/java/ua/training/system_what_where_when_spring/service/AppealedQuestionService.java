package ua.training.system_what_where_when_spring.service;

import org.springframework.stereotype.Service;
import ua.training.system_what_where_when_spring.entity.Appeal;
import ua.training.system_what_where_when_spring.entity.AppealedQuestion;
import ua.training.system_what_where_when_spring.entity.Question;

@Service
public class AppealedQuestionService {

    public AppealedQuestion toAppealedQuestion(Question question, Appeal appeal) {
        return AppealedQuestion.builder()
                .question(question)
                .appeal(appeal)
                .build();
    }
}