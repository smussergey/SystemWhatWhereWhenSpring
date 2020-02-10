package ua.training.game.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Slf4j
@Controller
public class LoginController {
    private final static String LOGIN_PAGE = "login";

    @GetMapping("/login")
    public String getLogin(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model, HttpServletRequest request, Principal principal) {
        logoutUserIfWasLoggedIn(request, principal);
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        addCurrentLocaleLanguageAttributeToModel(model);
        return LOGIN_PAGE;
    }

    private Model addCurrentLocaleLanguageAttributeToModel(Model model) {
        model.addAttribute("lang", LocaleContextHolder.getLocale().getLanguage());
        return model;
    }


    private void logoutUserIfWasLoggedIn(HttpServletRequest request, Principal principal) {
        if (principal != null)
            try {
                request.logout();
            } catch (ServletException e) {
                log.warn(e.getMessage());
            }
    }
}