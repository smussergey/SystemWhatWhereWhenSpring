package ua.training.system_what_where_when_spring.service;

import org.springframework.stereotype.Service;
import ua.training.system_what_where_when_spring.dto.GameDTO;
import ua.training.system_what_where_when_spring.entity.AppealStage;
import ua.training.system_what_where_when_spring.entity.Game;
import ua.training.system_what_where_when_spring.entity.User;
import ua.training.system_what_where_when_spring.util.ResourceBundleUtil;

@Service
public class GameDTOService {
    private static final String DELIMITER = ":";

    public GameDTO toGameDTO(Game game) {
        GameDTO gameDTO = GameDTO.builder()
                .id(game.getId())
                .date(game.getDate())//TODO LOCALE
                .firstPlayerNameUa(game.getFirstPlayer().getNameUa())
                .firstPlayerNameEn(game.getFirstPlayer().getNameEn())
                .secondPlayerNameUa(game.getSecondPlayer().getNameUa())
                .secondPlayerNameEn(game.getSecondPlayer().getNameEn())
                .scores(createScoresResults(game))
                .appealStage(AppealStage.NOT_FILED.name()) //TODO Correctly
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
}