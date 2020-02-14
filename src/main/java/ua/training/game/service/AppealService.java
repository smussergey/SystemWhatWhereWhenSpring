package ua.training.game.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.training.game.domain.*;
import ua.training.game.enums.AppealStage;
import ua.training.game.exception.EntityNotFoundException;
import ua.training.game.repository.AppealRepository;
import ua.training.game.web.dto.GameDTO;
import ua.training.game.web.dto.QuestionDTO;

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

    public GameDTO getGameInformationByIdForFileAppealForm(Principal principal, Long gameId) {
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

    public GameDTO getGameInformationByIdForApprovalToAppealForm(Long gameId) {
        Game appealedGame = gameService.findById(gameId);
        GameDTO gameDTO = gameService.toGameDTO(appealedGame);
        gameDTO.setQuestionDTOs(questionService.extractQuestionDTOsFromGame(appealedGame));

        return gameDTO;

    }


    public Appeal fileAppealAgainstGameQuestions(long[] appealedQuestionIds, Principal principal) {
        log.info("in fileAppealAgainstGameQuestions() - id []: {} successfully was got", appealedQuestionIds);
        List<Question> questionsThatWereAppealed = Arrays.stream(appealedQuestionIds)
                .mapToObj(questionService::findById) // TODO improve this method: too many calls to db (use "IN"/batch)
                .collect(Collectors.toList());

        Game appealedGame = getAppealedGame(questionsThatWereAppealed);

        log.info("fileAppealAgainstGameAnsweredQuestions() - appealedGame: {} successfully was find", appealedGame.getId());

        Appeal appeal = Appeal.builder()
                .date(LocalDate.now())
                .game(appealedGame)
                .user(userService.findUserByLogin(principal.getName()))
                .appealStage(AppealStage.FILED)
                .appealedQuestions(new ArrayList<>())
                .build();

        appeal.addAppealedQuestions(getAppealedQuestions(questionsThatWereAppealed, appeal));

        return save(appeal);
    }

    private Game getAppealedGame(List<Question> appealedQuestions) {
        return appealedQuestions.stream()
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException("In getAppealedGame: Can not fond game with any appealedQuestions"))
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

    public void approveAppealsAgainstGameAppealedQuestions(long[] approvedQuestionIds) {
        log.info("in approveAppealAgainstGameAnsweredQuestions() - id []: {} successfully was got", approvedQuestionIds);

        List<Question> approvedQuestions = Arrays.stream(approvedQuestionIds)
                .mapToObj(questionService::findById) // TODO improve this method: too many calls to db (use "IN")
                .collect(Collectors.toList());

        Game appealedGame = getAppealedGame(approvedQuestions);
        List<Appeal> consideredAppeals = appealedGame.getAppeals();

        changeUserWhoGotPointInApprovedQuestions(approvedQuestions, appealedGame);
        // TODO maybe move to separate method and update field through dirty checking
        changeAppealStageInConsideredAppeals(consideredAppeals);

        saveApprovedAppealsAgainstGameQuestions(approvedQuestions, consideredAppeals);
    }

    private void changeUserWhoGotPointInApprovedQuestions(List<Question> approvedQuestions, Game appealedGame) {

        List<AppealedQuestion> appealedQuestionsWhichWereApproved = appealedGame.getAppeals().stream()
                .flatMap(appeal -> appeal.getAppealedQuestions().stream())
                .filter(appealedQuestion -> approvedQuestions.contains(appealedQuestion.getQuestion()))
                .collect(Collectors.toList());

        approvedQuestions.stream()
                .forEach(approvedQuestion -> approvedQuestion.setUserWhoGotPoint(appealedQuestionsWhichWereApproved.stream()
                        .filter(appealedQuestionWhichWereApproved -> appealedQuestionWhichWereApproved.getQuestion().equals(approvedQuestion))
                        .findAny()
                        .orElseThrow(() -> new EntityNotFoundException("There is no any AppealedQuestion which was approved"))
                        .getAppeal()
                        .getUser()));
    }

    private void changeAppealStageInConsideredAppeals(List<Appeal> consideredAppeals) {
        consideredAppeals.stream()
                .forEach(appeal -> appeal.setAppealStage(AppealStage.CONSIDERED));
    }

    @Transactional
    public void saveApprovedAppealsAgainstGameQuestions(List<Question> approvedQuestions, List<Appeal> consideredAppeals) {
        questionService.saveAll(approvedQuestions);
        saveAll(consideredAppeals);
    }


    @Transactional
    public List<Appeal> saveAll(List<Appeal> appeals) {
        return appealRepository.saveAll(appeals);
    }

}
