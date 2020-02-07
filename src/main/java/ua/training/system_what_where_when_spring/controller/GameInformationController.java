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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.training.system_what_where_when_spring.dto.GameDTO;
import ua.training.system_what_where_when_spring.entity.AppealStage;
import ua.training.system_what_where_when_spring.entity.User;
import ua.training.system_what_where_when_spring.service.GameInformationService;
import ua.training.system_what_where_when_spring.service.UserService;
import ua.training.system_what_where_when_spring.util.ResourceBundleUtil;

import java.security.Principal;

@Slf4j
@Controller
public class GameInformationController {
    private final static String GAMES_STATISTICS_PAGE_PLAYER = "player/gamesstatisticsplayer";
    private final static String GAME_DETAILS_PAGE_PLAYER = "player/gamedetailsplayer";
    private final static String GAMES_STATISTICS_PAGE_REFEREE = "referee/gamesstatisticsreferee";
    private final static String GAME_DETAILS_PAGE_REFEREE = "referee/gamedetailsreferee";
    private final static int DEFAULT_PAGINATION_SIZE = 5;

    private final GameInformationService gameInformationService;
    private final UserService userService;

    public GameInformationController(GameInformationService gameInformationService, UserService userService) {
        this.gameInformationService = gameInformationService;
        this.userService = userService;
    }

    @GetMapping("/player/games/statistics")
    public String getGamesStatisticsForPlayer(@PageableDefault(sort = "date", direction = Sort.Direction.DESC, size = DEFAULT_PAGINATION_SIZE)
                                                      Pageable pageable, Model model, Principal principal) {
        Page<GameDTO> gameDTOs = gameInformationService.getGamesStatisticsForLoggedInPlayer(pageable, principal);
        model.addAttribute("gameDTOs", gameDTOs);
        addLocalizedLoggedInUserNameToModel(model);
        addCurrentLocaleLanguageAttributeToModel(model);
        return GAMES_STATISTICS_PAGE_PLAYER;
    }

    @PostMapping("/player/game/details")
    public String getGameDetailsForPlayer(@RequestParam(value = "gameid") Long gameId, Model model) {
        GameDTO gameDTO = gameInformationService.getGameFullStatisticsByIdForPlayer(gameId);
        model.addAttribute("gameDTO", gameDTO);
        addLocalizedLoggedInUserNameToModel(model);
        addCurrentLocaleLanguageAttributeToModel(model);
        return GAME_DETAILS_PAGE_PLAYER;
    }

    @GetMapping("/referee/games/statistics")
    public String getGamesStatisticsForReferee(@PageableDefault(sort = "date", direction = Sort.Direction.DESC, size = DEFAULT_PAGINATION_SIZE)
                                                           Pageable pageable, Model model) {
        Page<GameDTO> gameDTOs = gameInformationService.getGamesStatisticsByAllGamesAndPlayers(pageable);
        model.addAttribute("gameDTOs", gameDTOs);
        addLocalizedLoggedInUserNameToModel(model);
        addCurrentLocaleLanguageAttributeToModel(model);
        return GAMES_STATISTICS_PAGE_REFEREE;
    }

    @PostMapping("/referee/game/details")
    public String getGameDetailsForReferee(@RequestParam(value = "gameid") Long gameId, Model model) {
        GameDTO gameDTO = gameInformationService.getGameFullStatisticsByIdForReferee(gameId);
        model.addAttribute("gameDTO", gameDTO);
        model.addAttribute("appealStageFiled",
                ResourceBundleUtil.getBundleStringForAppealStage(AppealStage.FILED.name())); //TODO take into account date and 2 appeals
        addLocalizedLoggedInUserNameToModel(model);
        addCurrentLocaleLanguageAttributeToModel(model);
        return GAME_DETAILS_PAGE_REFEREE;
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
