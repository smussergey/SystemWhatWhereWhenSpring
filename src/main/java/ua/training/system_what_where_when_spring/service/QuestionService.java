package ua.training.system_what_where_when_spring.service;

import org.springframework.stereotype.Service;
import ua.training.system_what_where_when_spring.dto.QuestionDTO;
import ua.training.system_what_where_when_spring.entity.Question;
import ua.training.system_what_where_when_spring.entity.AppealStage;
import ua.training.system_what_where_when_spring.repository.AnsweredQuestionRepository;
import ua.training.system_what_where_when_spring.util.ResourceBundleUtil;

import java.util.List;

@Service
public class QuestionService {

    private final AnsweredQuestionRepository answeredQuestionRepository;

    public QuestionService(AnsweredQuestionRepository answeredQuestionRepository) {
        this.answeredQuestionRepository = answeredQuestionRepository;
    }

    //TODO refactor this method
//    public Question findAnsweredQuestionById(Long id) {
//        return answeredQuestionRepository.findById(id).get();
//    }

    public QuestionDTO toQuestionDTO(Question question) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setNameWhoGotPointUa(question.getUserWhoGotPoint().getNameUa());
        questionDTO.setNameWhoGotPointEn(question.getUserWhoGotPoint().getNameEn());
        questionDTO.setAppealStage(ResourceBundleUtil.getBundleStringForAppealStage(AppealStage.NOT_FILED.name())); // TODO using correct appeal stage

        return questionDTO;
    }

//        if(question.getAppeal()!=null)
//
//    {
//        questionDTO.setAppealStage(
//                ResourceBundleUtil.getBundleStringForAppealStage(
//                        question.getAppeal().getAppealStage().name()));
//
//    } else questionDTO.setAppealStage(
//            ResourceBundleUtil.getBundleStringForAppealStage(
//            AppealStage.NOT_FILED.name()));
//
//
//        return questionDTO;
//}

    public List<Question> saveAll(List<Question> questions) {
        return answeredQuestionRepository.saveAll(questions);
    }
}
