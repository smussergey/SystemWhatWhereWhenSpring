package ua.training.system_what_where_when_spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.training.system_what_where_when_spring.dto.GameDTO;
import ua.training.system_what_where_when_spring.dto.QuestionDTO;
import ua.training.system_what_where_when_spring.entity.*;
import ua.training.system_what_where_when_spring.repository.AppealRepository;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AppealService {
    private final UserService userService;
    private final AppealRepository appealRepository;
    private final GameService gameService;
    private final QuestionService questionService;
    private final AppealedQuestionService appealedQuestionService;

    public AppealService(UserService userService, AppealRepository appealRepository, GameService gameService, QuestionService questionService, AppealedQuestionService appealedQuestionService) {
        this.userService = userService;
        this.appealRepository = appealRepository;
        this.gameService = gameService;
        this.questionService = questionService;
        this.appealedQuestionService = appealedQuestionService;
    }

    public GameDTO getGameInformationByIdForAppealForm(Principal principal, Long gameId) {
        User loggedInUser = userService.findUserByLogin(principal.getName());
        Game appealedGame = gameService.findById(gameId);
        GameDTO gameDTO = gameService.toGameDTO(appealedGame);
        gameDTO.setQuestionDTOs(getQuestionDTOsForAppealForm(loggedInUser, appealedGame));

        return gameDTO;

    }

    private List<QuestionDTO> getQuestionDTOsForAppealForm(User loggedInUser, Game appealedGame) {
        return questionService.extractQuestionDTOsFromGame(appealedGame).stream()
                .peek(questionDTO -> {
                    if (!questionDTO.getNameWhoGotPointEn().equals(loggedInUser.getNameEn())) {
                        questionDTO.setAppealPossible(true);
                    } else {
                        questionDTO.setAppealPossible(false);
                    }
                })
                .collect(Collectors.toList());
    }


    public Appeal fileAppealAgainstGameQuestions(long[] appealedQuestionStringIds, Principal principal) {
        log.info("in fileAppealAgainstGameQuestions() - id []: {} successfully was got", appealedQuestionStringIds);
        List<Question> appealedQuestions = Arrays.stream(appealedQuestionStringIds)
//                .mapToLong(Long::valueOf)
                .mapToObj(questionService::findByid) // TODO improve this method: too many calls to db (use "IN")
                .collect(Collectors.toList());

        Game appealedGame = getAppealedGame(appealedQuestions);

        log.info("fileAppealAgainstGameAnsweredQuestions() - appealedGame: {} successfully was find", appealedGame.getId());

        Appeal appeal = Appeal.builder()
                .date(LocalDate.now())
                .game(appealedGame)
                .user(userService.findUserByLogin(principal.getName()))
                .appealStage(AppealStage.FILED)
                .appealedQuestions(new ArrayList<>())
                .build();

        appeal.addAppealedQuestions(getAppealedQuestions(appealedQuestions, appeal));

        return save(appeal);
    }

    private Game getAppealedGame(List<Question> appealedQuestions) {
        return appealedQuestions.stream()
                .findAny()
                .get()
                .getGame();
    }

    private List<AppealedQuestion> getAppealedQuestions(List<Question> questions, Appeal appeal) {
        return questions.stream()
                .map(question -> appealedQuestionService.toAppealedQuestion(question, appeal))
                .collect(Collectors.toList());
    }


    @Transactional
    public Appeal save(Appeal appeal) {
        return appealRepository.save(appeal);
    }

//    public List<Appeal> approveAppealsAgainstGameAnsweredQuestions(String[] approvedQuestionStringIds) {
//        log.info("in AppealServise: approveAppealAgainstGameAnsweredQuestions() - id: {} successfully was got", approvedQuestionStringIds[0]);
//        List<Question> questionsWithApprovedAppeal = Arrays.stream(approvedQuestionStringIds)
//                .mapToLong(Long::valueOf)
//                .mapToObj(answeredQuestionService::findAnsweredQuestionById)// TODO improve this method: too many calls to db (use "IN")
//                .collect(Collectors.toList());
//
//        answeredQuestionService.saveAll(questionsWithApprovedAppeal.stream()
//                .peek(aq -> aq.setUserWhoGotPoint(aq.getAppeal().getUser()))
//                .collect(Collectors.toList()));
//
//        Game appealedGame = questionsWithApprovedAppeal.stream()
//                .findAny()
//                .get()
//                .getGame();
//
//        // maybe move to separate method and update field through dirty checking
//        return saveAll(appealedGame.getAppeals().stream()
//                .peek(appeal -> appeal.setAppealStage(AppealStage.CONSIDERED))
//                .collect(Collectors.toList()));
//    }
//
//    @Transactional
//    public List<Appeal> saveAll(List<Appeal> appeals) {
//        return appealRepository.saveAll(appeals);
//    }

//    public List<Appeal> findAllByAppealStage(AppealStage appealStage) {
//        return appealRepository.findAllByAppealStage(appealStage);
//    }
}
