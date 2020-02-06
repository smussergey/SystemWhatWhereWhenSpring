//package ua.training.system_what_where_when_spring.controller;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import ua.training.system_what_where_when_spring.entity.Role;
//import ua.training.system_what_where_when_spring.entity.User;
//import ua.training.system_what_where_when_spring.service.NewGameService;
//import ua.training.system_what_where_when_spring.service.UserService;
//
//import java.util.List;
//
//@Slf4j
//@Controller
//@RequestMapping("/referee")
//public class NewGameController {
//    private final static String NEW_GAME_PAGE_REFEREE = "referee/newgamereferee";
//    private final static String REDIRECT_GAMES_STATISTICS_REFEREE = "redirect:/referee/games/statistics";
//
//    private final NewGameService newGameService;
//    private final UserService userService;
//
//    public NewGameController(NewGameService newGameService, UserService userService) {
//        this.newGameService = newGameService;
//        this.userService = userService;
//    }
//
//    @GetMapping("/games/new")
//    public String getPreparedForNewGame(Model model) {
//        List<User> players = userService.findAllUsersByRole(Role.ROLE_PLAYER);
//        model.addAttribute("players", players);
//        setLocalizedLoggedInUserName(model);
//        setCurrentLocaleLanguage(model);
//        return NEW_GAME_PAGE_REFEREE;
//    }
//
//    @PostMapping("/games/new")
//    public String playNewGame(@RequestParam(value = "playerid", required = true) Long playerId,
//                              @RequestParam(value = "opponentid", required = true) Long opponentId,
//                              @RequestParam(value = "maxscores", required = true) int maxNumberOfScoresToFinishGame,
//                              Model model) {
//        log.info("IN playNewGame - team id: {} successfully was got", playerId);
//        log.info("IN playNewGame - opponent id: {} successfully was got", opponentId);
//        log.info("IN playNewGame - number Of questions : {} successfully was got", maxNumberOfScoresToFinishGame);
//        newGameService.runNewGame(playerId, opponentId, maxNumberOfScoresToFinishGame);
//        return REDIRECT_GAMES_STATISTICS_REFEREE;
//    }
//
//    private Model setLocalizedLoggedInUserName(Model model) {
//        User loggedInUser = userService.findLoggedIndUser();
//        model.addAttribute("userNameEn", loggedInUser.getNameEn());
//        model.addAttribute("userNameUa", loggedInUser.getNameUa());
//        return model;
//    }
//
//    private Model setCurrentLocaleLanguage(Model model) {
//        model.addAttribute("lang", LocaleContextHolder.getLocale().getLanguage());
//        return model;
//    }
//}
