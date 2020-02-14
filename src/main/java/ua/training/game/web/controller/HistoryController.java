package ua.training.game.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.training.game.domain.User;
import ua.training.game.service.HistoryService;
import ua.training.game.service.UserService;
import ua.training.game.web.dto.GameDTO;

@Slf4j
@Controller
public class HistoryController {
    private final static String HISTORY_CONSIDERATION_PAGE_REFEREE = "referee/historyconsiderationreferee";
    private final static String HISTORY_GAMES_STATISTICS_PAGE_REFEREE = "referee/historygamesstatisticsreferee";
    private final static String REDIRECT_HISTORY_CONSIDERATION_PAGE_REFEREE = "redirect:/referee/history/consideration";
    private final static int DEFAULT_PAGINATION_SIZE = 5;

    private final HistoryService historyService;
    private final UserService userService;

    public HistoryController(HistoryService historyService, UserService userService) {
        this.historyService = historyService;
        this.userService = userService;
    }

    @GetMapping("/referee/history/consideration")
    public String getGamesWhichCanBeMovedToHistory(@PageableDefault(sort = "date", direction = Sort.Direction.DESC, size = DEFAULT_PAGINATION_SIZE) Pageable pageable, Model model) {

        Page<GameDTO> gameDTOs = historyService.getGamesWhichCanBeMovedToHistory(pageable);
        model.addAttribute("gameDTOs", gameDTOs);

        addLocalizedLoggedInUserNameToModel(model);
        addCurrentLocaleLanguageAttributeToModel(model);
        return HISTORY_CONSIDERATION_PAGE_REFEREE;
    }

    @PostMapping("/referee/history/game")
    public String moveGameToHistory(@RequestParam(value = "gameid") Long gameId, Model model) {
        historyService.moveGameToHistory(gameId);
        addLocalizedLoggedInUserNameToModel(model);
        addCurrentLocaleLanguageAttributeToModel(model);
        return REDIRECT_HISTORY_CONSIDERATION_PAGE_REFEREE;
    }

    @GetMapping("/referee/history/games/statistics")
    public String getHistoryOfGames(@PageableDefault(sort = "date", direction = Sort.Direction.DESC, size = DEFAULT_PAGINATION_SIZE)
                                            Pageable pageable, Model model) {
        Page<GameDTO> gameDTOs = historyService.getHistoryGameStatisticsByAllGamesAndPlayers(pageable);
        model.addAttribute("gameDTOs", gameDTOs);

        addLocalizedLoggedInUserNameToModel(model);
        addCurrentLocaleLanguageAttributeToModel(model);
        return HISTORY_GAMES_STATISTICS_PAGE_REFEREE;
    }

    private Model addLocalizedLoggedInUserNameToModel(Model model) {
        User loggedInUser = userService.findLoggedIndUser();
        model.addAttribute("userNameEn", loggedInUser.getNameEn());
        model.addAttribute("userNameUa", loggedInUser.getNameUa());
        return model;
    }

    private Model addCurrentLocaleLanguageAttributeToModel(Model model) {
        model.addAttribute("lang", LocaleContextHolder.getLocale().getLanguage());
        return model;
    }
}
