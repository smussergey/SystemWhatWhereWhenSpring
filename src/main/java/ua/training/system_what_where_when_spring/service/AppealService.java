package ua.training.system_what_where_when_spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.training.system_what_where_when_spring.entity.AnsweredQuestion;
import ua.training.system_what_where_when_spring.entity.Appeal;
import ua.training.system_what_where_when_spring.entity.AppealStage;
import ua.training.system_what_where_when_spring.entity.Game;
import ua.training.system_what_where_when_spring.repository.AppealRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AppealService {
    private final AnsweredQuestionService answeredQuestionService;
    private final UserService userService;
    private final AppealRepository appealRepository;

    public AppealService(AnsweredQuestionService answeredQuestionService, UserService userService, AppealRepository appealRepository) {
        this.answeredQuestionService = answeredQuestionService;
        this.userService = userService;
        this.appealRepository = appealRepository;
    }


    public Appeal fileAppealAgainstGameAnsweredQuestions(String[] appealedQuestionStringIds) {
        log.info("in AppealServise: fileAppealAgainstGameAnsweredQuestions() - id: {} successfully was got", appealedQuestionStringIds[0]);
        List<AnsweredQuestion> appealedQuestions = Arrays.stream(appealedQuestionStringIds)
                .mapToLong(Long::valueOf)
                .mapToObj(answeredQuestionService::findAnsweredQuestionById) // TODO improve this method: too many calls to db (use "IN")
                .collect(Collectors.toList());

        // TODO check if null is impossible
        Game appealedGame = appealedQuestions.stream()
                .findAny()
                .get()
                .getGame();

        log.info("in AppealService: fileAppealAgainstGameAnsweredQuestions() - appealedGame: {} successfully was find", appealedGame.getId());

        Appeal appeal = new Appeal();
        appeal.setDate(LocalDate.now());
        appeal.setUser(userService.findLoggedIndUser());
        appeal.setGame(appealedGame);
        appeal.addAnsweredQuestions(appealedQuestions);
        appeal.setAppealStage(AppealStage.FILED);
        return save(appeal);
    }

    @Transactional // TODO reduce operation in transaction
    public Appeal save(Appeal appeal) {
        return appealRepository.save(appeal);
    }

    public List<Appeal> approveAppealsAgainstGameAnsweredQuestions(String[] approvedQuestionStringIds) {
        log.info("in AppealServise: approveAppealAgainstGameAnsweredQuestions() - id: {} successfully was got", approvedQuestionStringIds[0]);
        List<AnsweredQuestion> answeredQuestionsWithApprovedAppeal = Arrays.stream(approvedQuestionStringIds)
                .mapToLong(Long::valueOf)
                .mapToObj(answeredQuestionService::findAnsweredQuestionById)// TODO improve this method: too many calls to db (use "IN")
                .collect(Collectors.toList());

        answeredQuestionService.saveAll(answeredQuestionsWithApprovedAppeal.stream()
                .peek(aq -> aq.setUserWhoGotPoint(aq.getAppeal().getUser()))
                .collect(Collectors.toList()));

        Game appealedGame = answeredQuestionsWithApprovedAppeal.stream()
                .findAny()
                .get()
                .getGame();

        // maybe move to separate method and update field through dirty checking
        return saveAll(appealedGame.getAppeals().stream()
                .peek(appeal -> appeal.setAppealStage(AppealStage.CONSIDERED))
                .collect(Collectors.toList()));
    }

    @Transactional
    public List<Appeal> saveAll(List<Appeal> appeals) {
        return appealRepository.saveAll(appeals);
    }

    public List<Appeal> findAllByAppealStage(AppealStage appealStage) {
        return appealRepository.findAllByAppealStage(appealStage);
    }
}
