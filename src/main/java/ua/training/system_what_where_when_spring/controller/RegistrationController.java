package ua.training.system_what_where_when_spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ua.training.system_what_where_when_spring.dto.UserRegistrationDTO;
import ua.training.system_what_where_when_spring.service.UserRegistrationService;
import ua.training.system_what_where_when_spring.util.validation.ValidationErrorBuilder;

import javax.validation.Valid;

@Slf4j
@Controller
public class RegistrationController {
    private final static String REGISTRATION_PAGE = "registration";
    private final static String REDIRECT_LOGIN_PAGE = "redirect:/login";
    private final static String LOGIN_PAGE = "login";

    private UserRegistrationService userRegistrationService;

    public RegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
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
                userRegistrationService.registerNewUser(userRegistrationDTO);
                return REDIRECT_LOGIN_PAGE;
            } catch (Exception ex) {
                log.warn(userRegistrationDTO.getEmail() + " email is already exist");
                addCurrentLocaleLanguageAttributeToModel(model);
                model.addAttribute("emailerror", "registration.message.login.already.exists");
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
