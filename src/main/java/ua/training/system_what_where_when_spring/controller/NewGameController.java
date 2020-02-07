package ua.training.system_what_where_when_spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.training.system_what_where_when_spring.entity.Role;
import ua.training.system_what_where_when_spring.entity.User;
import ua.training.system_what_where_when_spring.service.NewGameService;
import ua.training.system_what_where_when_spring.service.UserService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/referee")
public class NewGameController {
    private final static String NEW_GAME_PAGE_REFEREE = "referee/newgamereferee";
    private final static String REDIRECT_GAMES_STATISTICS_REFEREE = "redirect:/referee/games/statistics";

    private final NewGameService newGameService;
    private final UserService userService;

    public NewGameController(NewGameService newGameService, UserService userService) {
        this.newGameService = newGameService;
        this.userService = userService;
    }

    @GetMapping("/games/new")
    public String getPreparedForNewGame(Model model) {
        List<User> players = userService.findAllUsersByRole(Role.ROLE_PLAYER);
        model.addAttribute("players", players);
        addLocalizedLoggedInUserNameToModel(model);
        addCurrentLocaleLanguageAttributeToModel(model);
        return NEW_GAME_PAGE_REFEREE;
    }

    @PostMapping("/games/new") //TODO add validation for maxNumberOfScoresToFinishGame
    public String playNewGame(@RequestParam(value = "firstplayerid", required = true) Long firstPlayerId,
                              @RequestParam(value = "secondplayerid", required = true) Long secondPlayerId,
                              @RequestParam(value = "maxscores", required = true) int maxNumberOfScoresToFinishGame,
                              Model model) {
        log.info("IN playNewGame - firstPlayerId id: {} successfully was got", firstPlayerId);
        log.info("IN playNewGame - secondPlayerId id: {} successfully was got", secondPlayerId);
        log.info("IN playNewGame - number Of questions : {} successfully was got", maxNumberOfScoresToFinishGame);

        try {
            newGameService.runNewGame(firstPlayerId, secondPlayerId, maxNumberOfScoresToFinishGame);
            return REDIRECT_GAMES_STATISTICS_REFEREE;
        } catch (Exception ex) {
            log.error("IN NewGameController, method playNewGame- firstPlayerId: {} secondPlayerId {} are the same", firstPlayerId, secondPlayerId);
            addCurrentLocaleLanguageAttributeToModel(model);
            addLocalizedLoggedInUserNameToModel(model);
            model.addAttribute("error", "error.massage.two.players.cannot.be.the.same.name");
            return NEW_GAME_PAGE_REFEREE; //TODO add players names to model
        }
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
