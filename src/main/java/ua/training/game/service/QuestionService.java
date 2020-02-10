package ua.training.game.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.training.game.domain.Game;
import ua.training.game.domain.Question;
import ua.training.game.enums.AppealStage;
import ua.training.game.exception.EntityNotFoundException;
import ua.training.game.repository.QuestionRepository;
import ua.training.game.util.ResourceBundleUtil;
import ua.training.game.web.dto.QuestionDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    public Question findById(Long id) {
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
        game.getAppeals()
                .forEach(appeal -> appeal.getAppealedQuestions().stream()
                        .filter(appealedQuestion -> appealedQuestion.getQuestion().equals(question))
                        .findAny()
                        .ifPresent(appealedQuestion -> questionDTO.setAppealStage(ResourceBundleUtil
                                .getBundleStringForAppealStage(appealedQuestion
                                        .getAppeal()
                                        .getAppealStage()
                                        .name()))));
    }

    @Transactional
    public List<Question> saveAll(List<Question> questions) {
        return questionRepository.saveAll(questions);
    }
}
