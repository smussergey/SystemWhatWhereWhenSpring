package ua.training.system_what_where_when_spring.service;

import org.springframework.stereotype.Service;
import ua.training.system_what_where_when_spring.dto.AnsweredQuestionDTO;
import ua.training.system_what_where_when_spring.entity.AnsweredQuestion;
import ua.training.system_what_where_when_spring.entity.AppealStage;
import ua.training.system_what_where_when_spring.repository.AnsweredQuestionRepository;
import ua.training.system_what_where_when_spring.util.ResourceBundleUtil;

import java.util.List;

@Service
public class AnsweredQuestionService {

    private final AnsweredQuestionRepository answeredQuestionRepository;

    public AnsweredQuestionService(AnsweredQuestionRepository answeredQuestionRepository) {
        this.answeredQuestionRepository = answeredQuestionRepository;
    }

    //TODO refactor this method
    public AnsweredQuestion findAnsweredQuestionById(Long id) {
        return answeredQuestionRepository.findById(id).get();
    }

    public AnsweredQuestionDTO toAnsweredQuestionDTO(AnsweredQuestion answeredQuestion) {
        AnsweredQuestionDTO answeredQuestionDTO = new AnsweredQuestionDTO();
        answeredQuestionDTO.setId(answeredQuestion.getId());

        if (answeredQuestion.getUserWhoGotPoint() != null) {
            answeredQuestionDTO.setNameWhoGotPointUa(answeredQuestion.getUserWhoGotPoint().getNameUa());
            answeredQuestionDTO.setNameWhoGotPointEn(answeredQuestion.getUserWhoGotPoint().getNameEn());
        } else {
            answeredQuestionDTO.setNameWhoGotPointUa(ResourceBundleUtil.getBundleString("games.game.statistics.text.audience"));
            answeredQuestionDTO.setNameWhoGotPointEn(ResourceBundleUtil.getBundleString("games.game.statistics.text.audience"));
        }

        if (answeredQuestion.getAppeal() != null) {
            answeredQuestionDTO.setAppealStage(
                    ResourceBundleUtil.getBundleStringForAppealStage(
                            answeredQuestion.getAppeal().getAppealStage().name()));

        } else answeredQuestionDTO.setAppealStage(
                ResourceBundleUtil.getBundleStringForAppealStage(
                        AppealStage.NOT_FILED.name()));


        return answeredQuestionDTO;
    }

    public List<AnsweredQuestion> saveAll(List<AnsweredQuestion> answeredQuestions) {
        return answeredQuestionRepository.saveAll(answeredQuestions);
    }
}
