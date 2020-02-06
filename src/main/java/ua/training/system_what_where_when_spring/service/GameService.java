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


    @Transactional
    public void deleteGameById(Long id) {
        gameRepository.deleteById(id);
    }


    public GameDTO toGameDTO(Game game) {
        GameDTO gameDTO = GameDTO.builder()
                .id(game.getId())
                .date(game.getDate())//TODO LOCALE
                .firstPlayerNameUa(game.getFirstPlayer().getNameUa())
                .firstPlayerNameEn(game.getFirstPlayer().getNameEn())
                .secondPlayerNameUa(game.getSecondPlayer().getNameUa())
                .secondPlayerNameEn(game.getSecondPlayer().getNameEn())
                .scores(createScoresResults(game))
                .appealStage(ResourceBundleUtil.getBundleStringForAppealStage(getAppealStageForGameDTO(game).name()))
                .isAppealPossible(true)// TODO
                .build();

//        if (game.getAppeals().isEmpty()) {
//            gameDTO.setAppealStage(ResourceBundleUtil.getBundleStringForAppealStage(AppealStage.NOT_FILED.name()));
//        } else {
//            game.getAppeals().stream()
//                    .forEach(appeal -> {
//                        if (appeal.getAppealStage().equals(AppealStage.FILED)) {
//                            gameDTO.setAppealStage(ResourceBundleUtil.getBundleStringForAppealStage(AppealStage.FILED.name()));
//                        } else {
//                            gameDTO.setAppealStage(ResourceBundleUtil.getBundleStringForAppealStage(AppealStage.CONSIDERED.name()));
//                        }
//
//                    });
//        }
        return gameDTO;
    }

    private String createScoresResults(Game game) {
        User firstPlayer = game.getFirstPlayer();

        long firstPlayerScores = game.getQuestions()
                .stream()
                .filter(question -> firstPlayer.equals(question.getUserWhoGotPoint()))
                .count();

        long secondPlayerScores = game.getQuestions().size() - firstPlayerScores;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(firstPlayerScores);
        stringBuilder.append(DELIMITER);
        stringBuilder.append(secondPlayerScores);

        return stringBuilder.toString();
    }

    private AppealStage getAppealStageForGameDTO(Game game) {
        return game.getAppeals().stream()
                .map(Appeal::getAppealStage)
                .findFirst()
                .orElse(AppealStage.NOT_FILED);
    }
}
