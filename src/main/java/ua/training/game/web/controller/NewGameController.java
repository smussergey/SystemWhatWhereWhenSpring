package ua.training.game.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.training.game.domain.Game;
import ua.training.game.domain.User;
import ua.training.game.enums.Role;
import ua.training.game.exception.TwoPlayersTheSameException;
import ua.training.game.service.NewGameService;
import ua.training.game.service.UserService;
import ua.training.game.util.ResourceBundleUtil;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/referee")
public class NewGameController {
    private final static String NEW_GAME_PAGE_REFEREE = "referee/newgamereferee";
    private final static String REDIRECT_GAMES_STATISTICS_REFEREE = "redirect:/referee/games/statistics";
    private final static String REDIRECT_GAME_DETAILS_REFEREE = "redirect:/referee/game/";
    private final static String REDIRECT_NEW_GAME_REFEREE = "redirect:/referee/game/new";

    private final NewGameService newGameService;
    private final UserService userService;

    public NewGameController(NewGameService newGameService, UserService userService) {
        this.newGameService = newGameService;
        this.userService = userService;
    }

    @GetMapping("/game/new")
    public String getPreparedForNewGame(@ModelAttribute("error") String error, Model model) {
        List<User> players = userService.findAllUsersByRole(Role.ROLE_PLAYER);
        model.addAttribute("players", players);
        addLocalizedLoggedInUserNameToModel(model);
        addCurrentLocaleLanguageAttributeToModel(model);
        return NEW_GAME_PAGE_REFEREE;
    }

    @PostMapping("/game/new") //TODO add validation for maxNumberOfScoresToFinishGame
    public String playNewGame(@RequestParam(value = "firstplayerid") Long firstPlayerId,
                              @RequestParam(value = "secondplayerid") Long secondPlayerId,
                              @RequestParam(value = "maxscores") int maxNumberOfScoresToFinishGame,
                              Model model, RedirectAttributes redirectAttrs) {
        log.info("IN playNewGame - firstPlayerId id: {} successfully was got", firstPlayerId);
        log.info("IN playNewGame - secondPlayerId id: {} successfully was got", secondPlayerId);
        log.info("IN playNewGame - number Of questions : {} successfully was got", maxNumberOfScoresToFinishGame);

        try {
            Game newGame = newGameService.runNewGame(firstPlayerId, secondPlayerId, maxNumberOfScoresToFinishGame);
            return REDIRECT_GAME_DETAILS_REFEREE + newGame.getId(); //TODO add to servlet
        } catch (TwoPlayersTheSameException ex) {
            log.error("IN NewGameController, method playNewGame- firstPlayerId: {} secondPlayerId {} are the same", firstPlayerId, secondPlayerId);
            addCurrentLocaleLanguageAttributeToModel(model);
            addLocalizedLoggedInUserNameToModel(model);

            redirectAttrs.addAttribute("error", ResourceBundleUtil.getBundleString("game.new.error.massage.two.players.cannot.be.the.same.name"));
//            return "redirect:referee/game/new"; //TODO add players names to model
            return REDIRECT_NEW_GAME_REFEREE; //TODO add players names to model
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
