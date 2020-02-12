package ua.training.game.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.training.game.domain.Game;
import ua.training.game.domain.User;
import ua.training.game.enums.Role;
import ua.training.game.exception.TwoPlayersTheSameException;
import ua.training.game.service.NewGameService;
import ua.training.game.service.UserService;
import ua.training.game.util.ResourceBundleUtil;
import ua.training.game.util.validation.ValidationErrorBuilder;
import ua.training.game.web.dto.NewGameFormDataDTO;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/referee")
public class NewGameController {
    private final static String NEW_GAME_PAGE_REFEREE = "referee/newgamereferee";
    private final static String REDIRECT_GAME_DETAILS_REFEREE = "redirect:/referee/game/";
    private final static String REDIRECT_NEW_GAME_REFEREE = "redirect:/referee/game/new";

    private final NewGameService newGameService;
    private final UserService userService;

    public NewGameController(NewGameService newGameService, UserService userService) {
        this.newGameService = newGameService;
        this.userService = userService;
    }

    @GetMapping("/game/new")
    public String getPreparedForNewGame(@ModelAttribute("error") String error, @ModelAttribute("fielderrors") String fielderrors, Model model) {
        List<User> players = userService.findAllUsersByRole(Role.ROLE_PLAYER);
        model.addAttribute("players", players);
        addLocalizedLoggedInUserNameToModel(model);
        addCurrentLocaleLanguageAttributeToModel(model);
        return NEW_GAME_PAGE_REFEREE;
    }

    @PostMapping("/game/new") //TODO change in servlet
    public String registerNewUser(@ModelAttribute("newgamedata") @Valid NewGameFormDataDTO newGameFormDataDTO,
                                  Errors errors, Model model, RedirectAttributes redirectAttrs) {
        if (!errors.hasErrors()) {
            try {
                Game newGame = newGameService.runNewGame(newGameFormDataDTO);
                return REDIRECT_GAME_DETAILS_REFEREE + newGame.getId(); //TODO add to servlets
            } catch (TwoPlayersTheSameException ex) {
                log.error("In method playNewGame- firstPlayerId: {} secondPlayerId {} are the same", newGameFormDataDTO.getFirstPlayerId(), newGameFormDataDTO.getSecondPlayerId());
                redirectAttrs.addAttribute("error", ResourceBundleUtil.getBundleString("game.new.error.massage.two.players.cannot.be.the.same.name"));
                addCurrentLocaleLanguageAttributeToModel(model);
                addLocalizedLoggedInUserNameToModel(model);
                return REDIRECT_NEW_GAME_REFEREE; //TODO add players names to model
            }
        }
        addCurrentLocaleLanguageAttributeToModel(model);
        redirectAttrs.addAttribute("fielderrors", ValidationErrorBuilder.fromBindingErrors(errors).getErrors());
        return REDIRECT_NEW_GAME_REFEREE;
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
