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
import org.springframework.web.bind.annotation.PathVariable;
import ua.training.game.domain.User;
import ua.training.game.enums.AppealStage;
import ua.training.game.exception.AccessNotAllowedException;
import ua.training.game.service.GameInformationService;
import ua.training.game.service.UserService;
import ua.training.game.util.ResourceBundleUtil;
import ua.training.game.web.dto.GameDTO;

import java.security.Principal;

@Slf4j
@Controller
public class GameInformationController {
    private final static String GAMES_STATISTICS_PAGE_PLAYER = "player/gamesstatisticsplayer";
    private final static String GAME_DETAILS_PAGE_PLAYER = "player/gamedetailsplayer";
    private final static String GAMES_STATISTICS_PAGE_REFEREE = "referee/gamesstatisticsreferee";
    private final static String GAME_DETAILS_PAGE_REFEREE = "referee/gamedetailsreferee";
    private final static String ACCESS_DENIED_PAGE = "error/403";
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

    @GetMapping("/player/game/{id}") // TODO CHECK if wrong input
    public String getGameDetailsForPlayer(Model model, @PathVariable Long id, Principal principal) {
        try {
            GameDTO gameDTO = gameInformationService.getGameFullStatisticsByIdForPlayer(id, principal);
            model.addAttribute("gameDTO", gameDTO);
            addLocalizedLoggedInUserNameToModel(model);
            addCurrentLocaleLanguageAttributeToModel(model);
            return GAME_DETAILS_PAGE_PLAYER;
        } catch (AccessNotAllowedException ex) {
            log.warn("IN getGameDetailsForPlayer - user: {} is trying to get access to game {}, in which he didn't take part", principal.getName(), id);
        }
        return ACCESS_DENIED_PAGE;
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

    //TODO Correct in servlet
    @GetMapping("/referee/game/{id}") // TODO CHECK if wrong input
    public String getGameDetailsForReferee(Model model, @PathVariable Long id) {
        GameDTO gameDTO = gameInformationService.getGameFullStatisticsByIdForReferee(id);
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
