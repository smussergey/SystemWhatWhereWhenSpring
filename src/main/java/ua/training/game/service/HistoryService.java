package ua.training.game.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.training.game.domain.Game;
import ua.training.game.domain.History;
import ua.training.game.repository.HistoryRepository;
import ua.training.game.util.ResourceBundleUtil;
import ua.training.game.web.dto.GameDTO;

import java.time.LocalDate;

@Slf4j
@Service
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final GameService gameService;


    public HistoryService(HistoryRepository historyRepository, GameService gameService) {
        this.historyRepository = historyRepository;
        this.gameService = gameService;
    }

    //TODO improve query to DB
    //TODO improve to get games when appeal was not filed and 1 (or 10) date passed
    public Page<GameDTO> getGamesWhichCanBeMovedToHistory(Pageable pageable) {
        return gameService.findAllByDateBefore(LocalDate.now(), pageable)
                .map(gameService::toGameDTO);
    }

    public boolean moveGameToHistory(Long id) {
        Game game = gameService.findById(id);
        History history = toHistory(game);

        return saveToHistoryAndDeleteGameRecord(history, game);
    }

    private History toHistory(Game game) {
        return History.builder()
                .date(game.getDate())
                .firstPlayerNameUa(game.getFirstPlayer().getNameUa())
                .firstPlayerNameEn(game.getFirstPlayer().getNameEn())
                .secondPlayerNameUa(game.getSecondPlayer().getNameUa())
                .secondPlayerNameEn(game.getSecondPlayer().getNameEn())
                .scores(gameService.createScoresResultsForGameDTO(game))
                .appealStage(gameService.createAppealStageForGameDTO(game).name())
                .build();
    }

    @Transactional
    public boolean saveToHistoryAndDeleteGameRecord(History history, Game game) {
        try {
            save(history);
            gameService.deleteGame(game);
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
                .date(gameService.createLocalizedDateForGameDTO(history.getDate()))
                .firstPlayerNameUa(history.getFirstPlayerNameUa())
                .firstPlayerNameEn(history.getFirstPlayerNameEn())
                .secondPlayerNameUa(history.getSecondPlayerNameUa())
                .secondPlayerNameEn(history.getSecondPlayerNameEn())
                .scores(history.getScores())
                .appealStage(ResourceBundleUtil.getBundleStringForAppealStage(history.getAppealStage()))
                .build();
    }
}
