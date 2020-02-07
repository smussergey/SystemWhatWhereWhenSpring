package ua.training.system_what_where_when_spring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.training.system_what_where_when_spring.dto.GameDTO;
import ua.training.system_what_where_when_spring.entity.Appeal;
import ua.training.system_what_where_when_spring.entity.AppealStage;
import ua.training.system_what_where_when_spring.entity.Game;
import ua.training.system_what_where_when_spring.entity.User;
import ua.training.system_what_where_when_spring.exception.EntityNotFoundException;
import ua.training.system_what_where_when_spring.repository.GameRepository;
import ua.training.system_what_where_when_spring.util.ResourceBundleUtil;

import java.time.LocalDate;

@Service
public class GameService {
    private static final String DELIMITER = ":";
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game findById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can not fond game with id: " + id));
    }

    public Page<Game> findAllByFirstPlayerOrSecondPlayer(User firstPlayer, User secondPlayer, Pageable pageable) {
        return gameRepository.findAllByFirstPlayerOrSecondPlayer(firstPlayer, secondPlayer, pageable);
    }

    public Page<Game> findAll(Pageable pageable) {
        return gameRepository.findAll(pageable);
    }

    public Page<Game> findAllByDateAfter(LocalDate localDate, Pageable pageable) {
        return gameRepository.findAllByDateBefore(localDate, pageable);
    }

    @Transactional
    public Game save(Game game) {
        return gameRepository.save(game);
    }

    @Transactional
    public void deleteGameById(Long id) {
        gameRepository.deleteById(id);
    }


    public GameDTO toGameDTO(Game game) {
        return GameDTO.builder()
                .id(game.getId())
                .date(game.getDate())//TODO LOCALE
                .firstPlayerNameUa(game.getFirstPlayer().getNameUa())
                .firstPlayerNameEn(game.getFirstPlayer().getNameEn())
                .secondPlayerNameUa(game.getSecondPlayer().getNameUa())
                .secondPlayerNameEn(game.getSecondPlayer().getNameEn())
                .scores(createScoresResults(game))
                .appealStage(ResourceBundleUtil.getBundleStringForAppealStage(getAppealStageForGame(game).name()))
//                .isAppealPossible(true)// TODO check if it is needed
                .build();
    }

    public String createScoresResults(Game game) {
        User firstPlayer = game.getFirstPlayer();

        long firstPlayerScores = game.getQuestions()
                .stream()
                .filter(question -> firstPlayer.equals(question.getUserWhoGotPoint()))
                .count();

        long secondPlayerScores = game.getQuestions().size() - firstPlayerScores;

        return firstPlayerScores + DELIMITER + secondPlayerScores;
    }

    public AppealStage getAppealStageForGame(Game game) {
        return game.getAppeals().stream()
                .map(Appeal::getAppealStage)
                .findFirst()
                .orElse(AppealStage.NOT_FILED);
    }
}
