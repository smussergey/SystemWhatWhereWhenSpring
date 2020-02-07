package ua.training.system_what_where_when_spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.training.system_what_where_when_spring.dto.GameDTO;
import ua.training.system_what_where_when_spring.entity.Game;
import ua.training.system_what_where_when_spring.entity.User;
import ua.training.system_what_where_when_spring.exception.EntityNotFoundException;

import java.security.Principal;
import java.time.LocalDate;

@Slf4j
@Service
public class GameInformationService {

    private final UserService userService;
    private final QuestionService questionService;
    private final GameService gameService;

    public GameInformationService(UserService userService, QuestionService questionService, GameService gameService) {
        this.userService = userService;
        this.questionService = questionService;
        this.gameService = gameService;
    }

    public Page<GameDTO> getGamesStatisticsByAllGamesAndPlayers(Pageable pageable) {
        return gameService.findAll(pageable)
                .map(gameService::toGameDTO);
    }

    public Page<GameDTO> getGamesStatisticsForLoggedInPlayer(Pageable pageable, Principal principal) throws EntityNotFoundException {
        User player = userService.findUserByLogin(principal.getName());
        return gameService.findAllByFirstPlayerOrSecondPlayer(player, player, pageable)
                .map(gameService::toGameDTO);
    }

    public GameDTO getGameFullStatisticsByIdForReferee(Long id) {
        Game game = gameService.findById(id);
        GameDTO gameDTO = gameService.toGameDTO(game);
        gameDTO.setQuestionDTOs(questionService.extractQuestionDTOsFromGame(game));

        return gameDTO;
    }

    public GameDTO getGameFullStatisticsByIdForPlayer(Long id) {
        Game game = gameService.findById(id);
        GameDTO gameDTO = gameService.toGameDTO(game);
        gameDTO.setQuestionDTOs(questionService.extractQuestionDTOsFromGame(game));
        gameDTO.setAppealPossible(checkIfLoggedInUserCanFileAppealAgainstGame(game));

        return gameDTO;
    }

    private boolean checkIfLoggedInUserCanFileAppealAgainstGame(Game game) {
        return checkIfLoggedInUserCanFileAppealAgainstGameBecauseOfTime(game)
                && (!checkIfLoggedInUserFiledAppealOnThisGame(game));
    }

    private boolean checkIfLoggedInUserCanFileAppealAgainstGameBecauseOfTime(Game game) {
        return game.getDate().isEqual(LocalDate.now());
    }

    private boolean checkIfLoggedInUserFiledAppealOnThisGame(Game game) {
        return game.getAppeals().stream()
                .anyMatch(appeal -> appeal.getUser().equals(userService.findLoggedIndUser()));
    }
}
