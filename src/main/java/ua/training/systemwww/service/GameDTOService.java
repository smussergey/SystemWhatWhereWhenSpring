//package ua.training.system_what_where_when_spring.service;
//
//import org.springframework.stereotype.Service;
//import ua.training.system_what_where_when_spring.dto.GameDTO;
//import ua.training.system_what_where_when_spring.entity.AppealStage;
//import ua.training.system_what_where_when_spring.entity.Game;
//import ua.training.system_what_where_when_spring.entity.User;
//import ua.training.system_what_where_when_spring.util.ResourceBundleUtil;
//
//@Service
//public class GameDTOService {
//    private static final String DELIMITER = ":";
//
//    //TODO refactor this method
//    public GameDTO toGameDTO(Game game) {
//        GameDTO gameDTO = new GameDTO();
//
//        gameDTO.setId(game.getId());
//        gameDTO.setDate(game.getDate());
//        gameDTO.setPlayerNameUa(game.getUsers().get(0).getNameUa());//TODO improve
//        gameDTO.setPlayerNameEn(game.getUsers().get(0).getNameEn());//TODO improve
//
//        if (game.getUsers().size() > 1) { //TODO improve
//            gameDTO.setOpponentNameUa(game.getUsers().get(1).getNameUa());
//            gameDTO.setOpponentNameEn(game.getUsers().get(1).getNameEn());
//        } else {
//            gameDTO.setOpponentNameUa(ResourceBundleUtil.getBundleString("games.game.statistics.text.audience"));
//            gameDTO.setOpponentNameEn(ResourceBundleUtil.getBundleString("games.game.statistics.text.audience"));
//        }
//
//        User firstPlayer = game.getUsers().get(0); //TODO correct
//        long firstPlayerScores = game.getQuestions()
//                .stream()
//                .filter(aq -> firstPlayer.equals(aq.getUserWhoGotPoint()))
//                .count();
//
//        long secondPlayerScores = game.getQuestions()
//                .stream()
//                .count() - firstPlayerScores;
//
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(firstPlayerScores);
//        stringBuilder.append(DELIMITER);//TODO move ":" to properties
//        stringBuilder.append(secondPlayerScores);
//        String scores = stringBuilder.toString();
//        gameDTO.setScores(scores);
//
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
//        return gameDTO;
//    }
//
//}
