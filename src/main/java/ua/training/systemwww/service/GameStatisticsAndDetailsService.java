//package ua.training.system_what_where_when_spring.service;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import ua.training.system_what_where_when_spring.dto.AnsweredQuestionDTO;
//import ua.training.system_what_where_when_spring.dto.GameDTO;
//import ua.training.system_what_where_when_spring.entity.Game;
//import ua.training.system_what_where_when_spring.entity.User;
//import ua.training.system_what_where_when_spring.exception.EntityNotFoundException;
//import ua.training.system_what_where_when_spring.repository.GameRepository;
//
//import java.security.Principal;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service
//public class GameStatisticsAndDetailsService {
//
//    private final UserService userService;
//    private final GameRepository gameRepository;
//    private final AnsweredQuestionService answeredQuestionService;
//    private final GameDTOService gameDTOService;
//
//    public GameStatisticsAndDetailsService(UserService userService, GameRepository gameRepository, AnsweredQuestionService answeredQuestionService, GameDTOService gameDTOService) {
//        this.userService = userService;
//        this.gameRepository = gameRepository;
//        this.answeredQuestionService = answeredQuestionService;
//        this.gameDTOService = gameDTOService;
//    }
//
//    public Page<GameDTO> getGameStatisticsByAllGamesAndPlayers(Pageable pageable) {
//        return gameRepository.findAll(pageable)
//                .map(gameDTOService::toGameDTO);
//    }
//
//    public Page<GameDTO> getGamesStatisticsByLoggedInPlayer(Principal principal, Pageable pageable) throws EntityNotFoundException {
//        //TODO improve with Principal
//        return gameRepository.findAllByUsers(userService.findLoggedIndUser(), pageable)
//                .map(gameDTOService::toGameDTO);
//    }
//
//    //    TODO forbid logged user to see not his game results
//    public GameDTO getGameFullStatisticsById(Long id) {
//        Game game = findGameById(id);
//        GameDTO gameDTO = gameDTOService.toGameDTO(game);
//
//        List<AnsweredQuestionDTO> answeredQuestions = game.getQuestions().stream()
//                .map(answeredQuestionService::toAnsweredQuestionDTO)
//                .collect(Collectors.toList());
//
//        gameDTO.setAnsweredQuestionDTOs(answeredQuestions);
//        gameDTO.setAppealPossible(checkIfLoggedUserCanFileAppealAgainstGame(game));
//
//        return gameDTO;
//    }
//
//    // TODO improve this method
//    private boolean checkIfLoggedUserCanFileAppealAgainstGame(Game game) {
//
//        if (!game.getAppeals().isEmpty()) {
//            return !game.getAppeals().stream()
//                    .filter(appeal -> appeal.getUser().equals(userService.findLoggedIndUser()))
//                    .findAny()
//                    .isPresent();
//        } else {
//            return true;
//        }
//    }
//
//    //TODO check/allow only user's games
//    public GameDTO getGameFullStatisticsByIdForAppealForm(Long id) {
//        User loggedInUser = userService.findLoggedIndUser();
//        Game game = findGameById(id);
//        GameDTO gameDTO = gameDTOService.toGameDTO(game);
//
//        List<AnsweredQuestionDTO> answeredQuestions = game.getQuestions().stream()
//                .map(answeredQuestionService::toAnsweredQuestionDTO)
//                .peek(aqDTO -> {
//                    if (!aqDTO.getNameWhoGotPointEn().equals(loggedInUser.getNameEn())) {
//                        aqDTO.setAppealPossible(true);
//                    } else {
//                        aqDTO.setAppealPossible(false);
//                    }
//                })
//                .collect(Collectors.toList());
//
//        gameDTO.setAnsweredQuestionDTOs(answeredQuestions);
//
//        return gameDTO;
//    }
//
//    public Game findGameById(Long id) {
//        return gameRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Can not fond games with id: " + id));
//    }
//
//}
