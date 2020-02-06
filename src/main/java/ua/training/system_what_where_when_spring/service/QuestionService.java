package ua.training.system_what_where_when_spring.service;

import org.springframework.stereotype.Service;
import ua.training.system_what_where_when_spring.dto.QuestionDTO;
import ua.training.system_what_where_when_spring.entity.Appeal;
import ua.training.system_what_where_when_spring.entity.Game;
import ua.training.system_what_where_when_spring.entity.Question;
import ua.training.system_what_where_when_spring.entity.AppealStage;
import ua.training.system_what_where_when_spring.exception.EntityNotFoundException;
import ua.training.system_what_where_when_spring.repository.QuestionRepository;
import ua.training.system_what_where_when_spring.util.ResourceBundleUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    //TODO refactor this method
    public Question findByid(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can not fond question with id: " + id));
    }


    public List<QuestionDTO> extractQuestionDTOsFromGame(Game game) {
        return game.getQuestions().stream()
                .map(question -> toQuestionDTO(question, game))
                .collect(Collectors.toList());
    }


    private QuestionDTO toQuestionDTO(Question question, Game game) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setNameWhoGotPointUa(question.getUserWhoGotPoint().getNameUa());
        questionDTO.setNameWhoGotPointEn(question.getUserWhoGotPoint().getNameEn());
        setDefaultAppealStageForQuestionDTO(questionDTO);
        changeAppealStageForQuestionDTOIfAppealExists(question, questionDTO, game);

        return questionDTO;
    }

    private void setDefaultAppealStageForQuestionDTO(QuestionDTO questionDTO) {
        questionDTO.setAppealStage(ResourceBundleUtil.getBundleStringForAppealStage(AppealStage.NOT_FILED.name()));
    }

    private void changeAppealStageForQuestionDTOIfAppealExists(Question question, QuestionDTO questionDTO, Game game) {
        game.getAppeals().stream()
                .forEach(appeal -> appeal.getAppealedQuestions().stream()
                        .filter(appealedQuestion -> appealedQuestion.getQuestion().equals(question))
                        .findAny()
                        .ifPresent(appealedQuestion -> questionDTO.setAppealStage(ResourceBundleUtil
                                .getBundleStringForAppealStage(appealedQuestion
                                        .getAppeal()
                                        .getAppealStage()
                                        .name()))));
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
        return questionRepository.saveAll(questions);
    }
}
