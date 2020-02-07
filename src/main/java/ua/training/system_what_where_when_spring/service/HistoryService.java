package ua.training.system_what_where_when_spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.training.system_what_where_when_spring.dto.GameDTO;
import ua.training.system_what_where_when_spring.entity.Game;
import ua.training.system_what_where_when_spring.entity.History;
import ua.training.system_what_where_when_spring.exception.EntityNotFoundException;
import ua.training.system_what_where_when_spring.repository.GameRepository;
import ua.training.system_what_where_when_spring.repository.HistoryRepository;
import ua.training.system_what_where_when_spring.util.ResourceBundleUtil;

import java.time.LocalDate;

@Slf4j
@Service
public class HistoryService {
    private static final String DELIMITER = ":";


    private final HistoryRepository historyRepository;
    private final GameService gameService;


    public HistoryService(HistoryRepository historyRepository, GameService gameService) {
        this.historyRepository = historyRepository;
        this.gameService = gameService;
    }

    //TODO improve query to DB
    //TODO improve to get games when appeal was not filed and 1 (or 10) date passed
    public Page<GameDTO> getGamesWhichCanBeMovedToHistory(Pageable pageable) {
        return gameService.findAllByDateAfter(LocalDate.now(), pageable)
                .map(gameService::toGameDTO);
    }

    public boolean moveGameToHistory(Long id) {
        Game game = gameService.findById(id);
        History history = toHistory(game);

        return saveToHistoryAndDeleteGameRecord(history, id);
    }

    private History toHistory(Game game) {
        return History.builder()
                .date(game.getDate())
                .firstPlayerNameUa(game.getFirstPlayer().getNameUa())
                .firstPlayerNameEn(game.getFirstPlayer().getNameEn())
                .secondPlayerNameUa(game.getSecondPlayer().getNameUa())
                .secondPlayerNameEn(game.getSecondPlayer().getNameEn())
                .scores(gameService.createScoresResults(game))
                .appealStage(gameService.getAppealStageForGame(game).name())
                .build();
    }

    @Transactional
    public boolean saveToHistoryAndDeleteGameRecord(History history, Long id) {
        try {
            save(history);
            gameService.deleteGameById(id);
            return true;
        } catch (Exception ex) {
            //TODO implement if needed
        }
        return false;
    }

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
                .firstPlayerNameUa(history.getFirstPlayerNameUa())
                .firstPlayerNameEn(history.getFirstPlayerNameEn())
                .secondPlayerNameUa(history.getSecondPlayerNameUa())
                .secondPlayerNameEn(history.getSecondPlayerNameEn())
                .scores(history.getScores())
                .appealStage(ResourceBundleUtil.getBundleStringForAppealStage(history.getAppealStage()))
                .build();
    }
}
