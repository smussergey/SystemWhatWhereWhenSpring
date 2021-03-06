package ua.training.game.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.training.game.service.UserService;
import ua.training.game.util.validation.ValidationErrorBuilder;
import ua.training.game.web.dto.UserRegistrationDTO;

import javax.validation.Valid;

@Slf4j
@Controller
public class RegistrationController {
    private final static String REGISTRATION_PAGE = "registration";
    private final static String REDIRECT_LOGIN_PAGE = "redirect:/login";

    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        addCurrentLocaleLanguageAttributeToModel(model);
        return REGISTRATION_PAGE;
    }

    @PostMapping("/registration")
    public String registerNewUser(@ModelAttribute("newuser") @Valid UserRegistrationDTO userRegistrationDTO,
                                  Errors errors, Model model) {
        if (!errors.hasErrors()) {
            try {
                userService.registerNewUser(userRegistrationDTO);
                return REDIRECT_LOGIN_PAGE;
            } catch (Exception ex) {
                log.warn(userRegistrationDTO.getEmail() + " email is already exist");
                addCurrentLocaleLanguageAttributeToModel(model);
                model.addAttribute("emailerror", "registration.error.message.login.already.exists");
                return REGISTRATION_PAGE;
            }
        }
        addCurrentLocaleLanguageAttributeToModel(model);
        model.addAttribute("fielderrors", ValidationErrorBuilder.fromBindingErrors(errors).getErrors());
        return REGISTRATION_PAGE;
    }


    private Model addCurrentLocaleLanguageAttributeToModel(Model model) {
        model.addAttribute("lang", LocaleContextHolder.getLocale().getLanguage());
        return model;
    }

}
