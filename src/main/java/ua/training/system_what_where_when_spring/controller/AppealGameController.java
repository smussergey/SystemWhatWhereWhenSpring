//package ua.training.system_what_where_when_spring.controller;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import ua.training.system_what_where_when_spring.dto.GameDTO;
//import ua.training.system_what_where_when_spring.entity.AppealStage;
//import ua.training.system_what_where_when_spring.entity.User;
//import ua.training.system_what_where_when_spring.service.AppealService;
//import ua.training.system_what_where_when_spring.service.GameStatisticsAndDetailsService;
//import ua.training.system_what_where_when_spring.service.UserService;
//import ua.training.system_what_where_when_spring.util.ResourceBundleUtil;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Slf4j
//@Controller
//public class AppealGameController {
//    private final static String FILE_APPEAL_FORM_PAGE_PLAYER = "player/fileappealformplayer";
//    private final static String REDIRECT_GAMES_STATISTICS_PLAYER = "redirect:/player/games/statistics";
//    private final static String APPEAL_CONSIDERATION_FORM_PAGE_REFEREE = "referee/appealconsiderationformreferee";
//    private final static String REDIRECT_GAMES_STATISTICS_REFEREE = "redirect:/referee/games/statistics";
//
//    private final GameStatisticsAndDetailsService gameStatisticsAndDetailsService;
//    private final UserService userService;
//    private final AppealService appealService;
//
//    public AppealGameController(GameStatisticsAndDetailsService gameStatisticsAndDetailsService, UserService userService, AppealService appealService) {
//        this.gameStatisticsAndDetailsService = gameStatisticsAndDetailsService;
//        this.userService = userService;
//        this.appealService = appealService;
//    }
//
//
//    @GetMapping("/player/appeal/games/{id}")
//    public String getFileAppealForm(Model model, @PathVariable Long id) {
//        GameDTO gameDTO = gameStatisticsAndDetailsService.getGameFullStatisticsByIdForAppealForm(id);
//        model.addAttribute("gameDTO", gameDTO);
//        setLocalizedLoggedInUserName(model);
//        setCurrentLocaleLanguage(model);
//        return FILE_APPEAL_FORM_PAGE_PLAYER;
//    }
//
//    @PostMapping("/player/appeal/game/questions")
//    public String fileAppealAgainstAnsweredQuestions(HttpServletRequest request, Model model) {
//        String[] appealedAnsweredQuestionIds = request.getParameterValues("ids");
//        if (appealedAnsweredQuestionIds.length > 0) {
//            log.info("IN appealQuastions - appealed questions {} successfully were got", appealedAnsweredQuestionIds.length);
//            appealService.fileAppealAgainstGameAnsweredQuestions(appealedAnsweredQuestionIds);
//        }
//        return REDIRECT_GAMES_STATISTICS_PLAYER;
//    }
//
//
//    @GetMapping("/referee/appeal/games/{id}")
//    public String getConsiderationAppealForm(Model model, @PathVariable Long id) {
//        GameDTO gameDTO = gameStatisticsAndDetailsService.getGameFullStatisticsById(id);
//        model.addAttribute("gameDTO", gameDTO);
//        model.addAttribute("appealStageFiled",
//                ResourceBundleUtil.getBundleStringForAppealStage(AppealStage.FILED.name()));
//        setLocalizedLoggedInUserName(model);
//        setCurrentLocaleLanguage(model);
//        return APPEAL_CONSIDERATION_FORM_PAGE_REFEREE;
//    }
//
//    @PostMapping("/referee/appeal/game/quastions")
//    public String approveAppealsAgainstGameAnsweredQuestions(HttpServletRequest request, Model model) {
//        String[] answeredQuestionIds = request.getParameterValues("ids");
//        if (answeredQuestionIds.length > 0) {
//            log.info("IN considerAppealedQuestions - question with id: {} successfully was got", answeredQuestionIds.length);
//            appealService.approveAppealsAgainstGameAnsweredQuestions(answeredQuestionIds);
//        }
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
