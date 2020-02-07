package ua.training.system_what_where_when_spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.training.system_what_where_when_spring.dto.GameDTO;
import ua.training.system_what_where_when_spring.entity.AppealStage;
import ua.training.system_what_where_when_spring.entity.User;
import ua.training.system_what_where_when_spring.service.AppealService;
import ua.training.system_what_where_when_spring.service.GameInformationService;
import ua.training.system_what_where_when_spring.service.UserService;
import ua.training.system_what_where_when_spring.util.ResourceBundleUtil;

import java.security.Principal;

@Slf4j
@Controller
public class AppealGameController {
    private final static String FILE_APPEAL_FORM_PAGE_PLAYER = "player/fileappealformplayer";
    private final static String REDIRECT_GAMES_STATISTICS_PLAYER = "redirect:/player/games/statistics";
    private final static String APPEAL_CONSIDERATION_FORM_PAGE_REFEREE = "referee/appealconsiderationformreferee";
    private final static String REDIRECT_GAMES_STATISTICS_REFEREE = "redirect:/referee/games/statistics";

    private final GameInformationService gameInformationService;
    private final UserService userService;
    private final AppealService appealService;

    public AppealGameController(GameInformationService gameInformationService, UserService userService, AppealService appealService) {
        this.gameInformationService = gameInformationService;
        this.userService = userService;
        this.appealService = appealService;
    }


    @PostMapping("/player/appeal/game")
    public String getAppealFormForGame(@RequestParam(value = "gameid") Long gameId,
                                       Principal principal, Model model) {
        GameDTO gameDTO = appealService.getGameInformationByIdForFileAppealForm(principal, gameId);
        model.addAttribute("gameDTO", gameDTO);
        addLocalizedLoggedInUserNameToModel(model);
        addCurrentLocaleLanguageAttributeToModel(model);
        return FILE_APPEAL_FORM_PAGE_PLAYER;
    }

    @PostMapping("/player/appeal/game/questions") // TODO add error if nothing got
    public String fileAppealAgainstAnsweredQuestions(@RequestParam(value = "ids") long[] ids,
                                                     Principal principal, Model model) {
        if (ids.length > 0) {
            log.info("IN appealQuastions - appealed questions {} successfully were got", ids.length);
            appealService.fileAppealAgainstGameQuestions(ids, principal);
        }
        return REDIRECT_GAMES_STATISTICS_PLAYER;
    }

    @PostMapping("/referee/appeal/game")
    public String getConsiderationAppealForm(@RequestParam(value = "gameid") Long gameId, Model model) {
        GameDTO gameDTO = appealService.getGameInformationByIdForApprovalToAppealForm(gameId);
        model.addAttribute("gameDTO", gameDTO);
        model.addAttribute("appealStageFiled",
                ResourceBundleUtil.getBundleStringForAppealStage(AppealStage.FILED.name()));
        addLocalizedLoggedInUserNameToModel(model);
        addCurrentLocaleLanguageAttributeToModel(model);
        return APPEAL_CONSIDERATION_FORM_PAGE_REFEREE;
    }

    @PostMapping("/referee/appeal/game/quastions")// TODO add error if nothing got
    public String approveAppealsAgainstGameAnsweredQuestions(@RequestParam(value = "ids") long[] ids, Model model) {
        if (ids.length > 0) {
            log.info("IN considerAppealedQuestions - appealed questions {} successfully were got", ids.length);
            appealService.approveAppealsAgainstGameAnsweredQuestions(ids);
        }

        return REDIRECT_GAMES_STATISTICS_REFEREE;
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
