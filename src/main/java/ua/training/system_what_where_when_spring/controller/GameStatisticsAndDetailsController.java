package ua.training.system_what_where_when_spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.training.system_what_where_when_spring.dto.GameDTO;
import ua.training.system_what_where_when_spring.exception.EntityNotFoundException;
import ua.training.system_what_where_when_spring.entity.AppealStage;
import ua.training.system_what_where_when_spring.entity.User;
import ua.training.system_what_where_when_spring.service.GameStatisticsAndDetailsService;
import ua.training.system_what_where_when_spring.service.UserService;
import ua.training.system_what_where_when_spring.util.ResourceBundleUtil;

import java.security.Principal;

@Slf4j
@Controller
public class GameStatisticsAndDetailsController {
    private final static String GAMES_STATISTICS_PAGE_PLAYER = "player/gamesstatisticsplayer";
    private final static String GAME_DETAILS_PAGE_PLAYER = "player/gamedetailsplayer";
    private final static String GAMES_STATISTICS_PAGE_REFEREE = "referee/gamesstatisticsreferee";
    private final static String GAME_DETAILS_PAGE_REFEREE = "referee/gamedetailsreferee";
    private final static int DEFAULT_PAGINATION_SIZE = 5;

    private final GameStatisticsAndDetailsService gameStatisticsAndDetailsService;
    private final UserService userService;

    public GameStatisticsAndDetailsController(GameStatisticsAndDetailsService gameStatisticsAndDetailsService, UserService userService) {
        this.gameStatisticsAndDetailsService = gameStatisticsAndDetailsService;
        this.userService = userService;
    }

    @GetMapping("/player/games/statistics")
    public String getGamesStatisticsForPlayer(@PageableDefault(sort = "date", size = DEFAULT_PAGINATION_SIZE, direction = Sort.Direction.DESC)
                                                      Pageable pageable, Model model, Principal principal) {
        Page<GameDTO> gameDTOs = gameStatisticsAndDetailsService.getGamesStatisticsByLoggedInPlayer(principal, pageable);
        model.addAttribute("gameDTOs", gameDTOs);
        setLocalizedLoggedInUserName(model);
        setCurrentLocaleLanguage(model);
        return GAMES_STATISTICS_PAGE_PLAYER;
    }

    @PostMapping("/player/games/details")
    public String getGameDetailsForPlayer(@RequestParam(value = "gameid", required = true) Long gameId, Model model) {
        GameDTO gameDTO = gameStatisticsAndDetailsService.getGameFullStatisticsById(gameId);
        model.addAttribute("gameDTO", gameDTO);
        setLocalizedLoggedInUserName(model);
        setCurrentLocaleLanguage(model);
        return GAME_DETAILS_PAGE_PLAYER;
    }

    @GetMapping("/referee/games/statistics")
    public String getGamesStatisticsForReferee(@PageableDefault(sort = "date", size = DEFAULT_PAGINATION_SIZE, direction = Sort.Direction.DESC)
                                                       Pageable pageable, Model model) {
        Page<GameDTO> gameDTOs = gameStatisticsAndDetailsService.getGameStatisticsByAllGamesAndPlayers(pageable);
        model.addAttribute("gameDTOs", gameDTOs);
        setLocalizedLoggedInUserName(model);
        setCurrentLocaleLanguage(model);
        return GAMES_STATISTICS_PAGE_REFEREE;
    }

    @PostMapping("/referee/games/details")
    public String getGameDetailsForReferee(@RequestParam(value = "gameid", required = true) Long gameId, Model model) {
        GameDTO gameDTO = gameStatisticsAndDetailsService.getGameFullStatisticsById(gameId);
        model.addAttribute("gameDTO", gameDTO);
        model.addAttribute("appealStageFiled",
                ResourceBundleUtil.getBundleStringForAppealStage(AppealStage.FILED.name()));
        setLocalizedLoggedInUserName(model);
        setCurrentLocaleLanguage(model);
        return GAME_DETAILS_PAGE_REFEREE;
    }

    private Model setLocalizedLoggedInUserName(Model model) {
        User loggedInUser = userService.findLoggedIndUser();
        model.addAttribute("userNameEn", loggedInUser.getNameEn());
        model.addAttribute("userNameUa", loggedInUser.getNameUa());
        return model;
    }

    private Model setCurrentLocaleLanguage(Model model) {
        model.addAttribute("lang", LocaleContextHolder.getLocale().getLanguage());
        return model;
    }
}
