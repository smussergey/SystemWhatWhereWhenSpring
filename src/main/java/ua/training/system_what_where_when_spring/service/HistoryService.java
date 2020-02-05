package ua.training.system_what_where_when_spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.training.system_what_where_when_spring.dto.GameDTO;
import ua.training.system_what_where_when_spring.exception.EntityNotFoundException;
import ua.training.system_what_where_when_spring.repository.HistoryRepository;
import ua.training.system_what_where_when_spring.repository.GameRepository;
import ua.training.system_what_where_when_spring.util.ResourceBundleUtil;
import ua.training.system_what_where_when_spring.entity.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HistoryService {
    private static final String DELIMITER = ":";

    private final AppealService appealService;
    private final GameRepository gameRepository;
    private final HistoryRepository historyRepository;
    private final GameService gameService;
    private final GameDTOService gameDTOService;

    public HistoryService(AppealService appealService, GameRepository gameRepository, HistoryRepository historyRepository, GameService gameService, GameDTOService gameDTOService) {
        this.appealService = appealService;
        this.gameRepository = gameRepository;
        this.historyRepository = historyRepository;
        this.gameService = gameService;
        this.gameDTOService = gameDTOService;
    }

    //TODO improve query to DB
    //TODO improve to get games when appeal was not filed and 1 (or 10) date passed
    public List<GameDTO> getGamesWhichCanBeMovedToHistory() {
        return appealService.findAllByAppealStage(AppealStage.CONSIDERED)
                .stream()
                .map(Appeal::getGame)
                .map(gameDTOService::toGameDTO)
                .collect(Collectors.toList());
    }

    public boolean moveGameToHistory(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can not fond games with id: " + id));
        History history = toHistory(game);

        return saveToHistoryAndDeleteGameRecord(history, id);
    }

    @Transactional
    public boolean saveToHistoryAndDeleteGameRecord(History history, Long id) {
        try {
            save(history);
            gameService.deleteGameById(id);
            return true;
        } catch (Exception ex) {
            //TODO implement
        }
        return false;
    }

    //TODO refactor this method
    private History toHistory(Game game) {

        History history = new History();

        history.setDate(game.getDate());
        history.setPlayerNameUa(game.getUsers().get(0).getNameUa());//TODO improve
        history.setPlayerNameEn(game.getUsers().get(0).getNameEn());//TODO improve

        if (game.getUsers().size() > 1) { //TODO improve
            history.setOpponentNameUa(game.getUsers().get(1).getNameUa());
            history.setOpponentNameEn(game.getUsers().get(1).getNameEn());
        } else {
            //TODO correct for both languages
            history.setOpponentNameUa(ResourceBundleUtil.getBundleString("games.game.statistics.text.audience"));
            history.setOpponentNameEn(ResourceBundleUtil.getBundleString("games.game.statistics.text.audience"));
        }

        User firstPlayer = game.getUsers().get(0); //TODO correct
        long firstPlayerScores = game.getAnsweredQuestions()
                .stream()
                .filter(aq -> firstPlayer.equals(aq.getUserWhoGotPoint()))
                .count();

        long secondPlayerScores = (long) game.getAnsweredQuestions()
                .size() - firstPlayerScores;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(firstPlayerScores);
        stringBuilder.append(DELIMITER);//TODO move ":" to properties
        stringBuilder.append(secondPlayerScores);
        String scores = stringBuilder.toString();
        history.setScores(scores);
        //TODO correct for both languages
        game.getAppeals().stream()
                .forEach(appeal -> {
                    if (appeal.getAppealStage().equals(AppealStage.CONSIDERED)) {
                        history.setAppealStage(ResourceBundleUtil.getBundleStringForAppealStage(AppealStage.CONSIDERED.name()));
                    } else {
                        history.setAppealStage(ResourceBundleUtil.getBundleStringForAppealStage(AppealStage.NOT_FILED.name()));
                    }

                });
//        }
        return history;

    }

    //TODO refactor this method
    @Transactional
    public History save(History history) {
        return historyRepository.save(history);
    }

    public Page<History> findAll(Pageable pageable) {
        return historyRepository.findAll(pageable);
    }

    public Page<GameDTO> getHistoryGameStatisticsByAllGamesAndPlayers(Pageable pageable) {
        return findAll(pageable)
                .map(this::historyToGameDTO);
    }

    private GameDTO historyToGameDTO(History history) {
        return GameDTO.builder()
                .date(history.getDate())
                .playerNameUa(history.getPlayerNameUa())
                .playerNameEn(history.getPlayerNameEn())
                .opponentNameUa(history.getOpponentNameUa())
                .opponentNameEn(history.getOpponentNameEn())
                .scores(history.getScores())
                .appealStage(history.getAppealStage())
                .build();
    }
}
