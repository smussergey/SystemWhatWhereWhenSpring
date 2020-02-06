//package ua.training.system_what_where_when_spring.controller;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import ua.training.system_what_where_when_spring.dto.GameDTO;
//import ua.training.system_what_where_when_spring.exception.EntityNotFoundException;
//import ua.training.system_what_where_when_spring.entity.User;
//import ua.training.system_what_where_when_spring.service.HistoryService;
//import ua.training.system_what_where_when_spring.service.UserService;
//
//import java.util.List;
//import java.util.Set;
//
//@Slf4j
//@Controller
//public class HistoryController {
//    private final static String HISTORY_CONSIDERATION_PAGE_REFEREE = "referee/historyconsiderationreferee";
//    private final static String HISTORY_STATISTICS_PAGE_REFEREE = "referee/historystatisticsreferee";
//    private final static String REDIRECT_HISTORY_CONSIDERATION_PAGE_REFEREE = "redirect:/referee/history/consideration";
//    private final static int DEFAULT_PAGINATION_SIZE = 5;
//
//    private final HistoryService historyService;
//    private final UserService userService;
//
//    public HistoryController(HistoryService historyService, UserService userService) {
//        this.historyService = historyService;
//        this.userService = userService;
//    }
//
//    @GetMapping("/referee/history/consideration")
//    public String getGamesWhichCanBeMovedToHistory(Model model) {
//        try {
//            List<GameDTO> gameDTOs = historyService.getGamesWhichCanBeMovedToHistory();
//            model.addAttribute("gameDTOs", gameDTOs);
//        } catch (EntityNotFoundException ex) { // TODO chech if it should be here
//            log.warn("IN getGamesStatistics - cannot find games statistics for logged in user");
//        }
//        setLocalizedLoggedInUserName(model);
//        setCurrentLocaleLanguage(model);
//        return HISTORY_CONSIDERATION_PAGE_REFEREE;
//    }
//
////TODO change to delete
//    @GetMapping("/referee/history/game/{id}") //TODO check user can get info only on his game
//    public String moveGameToHistory(Model model, @PathVariable Long id) {
//        historyService.moveGameToHistory(id);
//        setLocalizedLoggedInUserName(model);
//        setCurrentLocaleLanguage(model);
//        return REDIRECT_HISTORY_CONSIDERATION_PAGE_REFEREE;
//    }
//
//    @GetMapping("/referee/history/games")
//    public String getHistoryOfGames(@PageableDefault(sort = "date", size = DEFAULT_PAGINATION_SIZE, direction = Sort.Direction.DESC)
//                                            Pageable pageable, Model model) {
//        try {
//            Page<GameDTO> gameDTOs = historyService.getHistoryGameStatisticsByAllGamesAndPlayers(pageable);
//            model.addAttribute("gameDTOs", gameDTOs);
//        } catch (EntityNotFoundException ex) { // TODO chech if it should be here
//            log.warn("IN getGamesStatistics - cannot find games statistics for logged in user");
//        }
//        setLocalizedLoggedInUserName(model);
//        setCurrentLocaleLanguage(model);
//        return HISTORY_STATISTICS_PAGE_REFEREE;
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
