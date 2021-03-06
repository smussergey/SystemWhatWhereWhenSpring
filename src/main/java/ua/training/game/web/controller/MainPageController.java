package ua.training.game.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.training.game.domain.User;
import ua.training.game.enums.Role;
import ua.training.game.service.UserService;

import java.security.Principal;

//TODO add exception handler
//TODO add error pages
@Slf4j
@Controller
public class MainPageController {
    private final static String HOME_PAGE_DEFAULT = "index";
    private final static String HOME_PAGE_PLAYER = "player/homeplayer";
    private final static String HOME_PAGE_REFEREE = "referee/homereferee";
    private final static String REDIRECT_HOME_PAGE_PLAYER = "redirect:/player/home";
    private final static String REDIRECT_HOME_PAGE_REFEREE = "redirect:/referee/home";

    private final UserService userService;

    public MainPageController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getMainPage(Model model, Principal principal) {
        addCurrentLocaleLanguageAttributeToModel(model);

        if (principal == null) {
            log.info("IN MainPageController - principal: {}", principal);
            return HOME_PAGE_DEFAULT;
        }

        Role role = userService.findUserByLogin(principal.getName()).getRole();
        log.info("IN MainPageController - principal logged with name {} and role {}", principal.getName(), role.name());
        switch (role) {
            case ROLE_PLAYER:
                return REDIRECT_HOME_PAGE_PLAYER;
            case ROLE_REFEREE:
                return REDIRECT_HOME_PAGE_REFEREE;
        }
        return HOME_PAGE_DEFAULT;
    }

    @GetMapping("/home")
    public String getDefaultPage(Model model) {
        addCurrentLocaleLanguageAttributeToModel(model);
        return HOME_PAGE_DEFAULT;
    }

    @GetMapping("/player/home")
    public String getHomePagePLAYER(Model model) {
        addLocalizedLoggedInUserNameToModel(model);
        addCurrentLocaleLanguageAttributeToModel(model);
        return HOME_PAGE_PLAYER;
    }

    @GetMapping("/referee/home")
    public String getHomePageReferee(Model model) {
        addLocalizedLoggedInUserNameToModel(model);
        addCurrentLocaleLanguageAttributeToModel(model);
        return HOME_PAGE_REFEREE;
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