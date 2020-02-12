//package ua.training.game.service.util;
//
//import org.springframework.stereotype.Service;
//import ua.training.game.enums.Role;
//import ua.training.game.exception.IncorrectInputForNewGameException;
//import ua.training.game.service.UserService;
//import ua.training.game.web.dto.NewGameFormDataDTO;
//
//@Service
//public class ValidationNewGameInputDataService {
//    private final UserService userService;
//
//    public ValidationNewGameInputDataService(UserService userService) {
//        this.userService = userService;
//    }
//
//    public boolean doesPlayerExistWithId(NewGameFormDataDTO newGameFormDataDTO) {
//        if (!userService.findUserByIdAndRole(newGameFormDataDTO.getFirstPlayerId(), Role.ROLE_PLAYER).isPresent()) {
//            throw new IncorrectInputForNewGameException("User does not exist");
//        }
//        return true;
//    }
//
//    public boolean doesPlayerExistWithId(NewGameFormDataDTO newGameFormDataDTO) {
//        if (!userService.findUserByIdAndRole(newGameFormDataDTO.getFirstPlayerId(), Role.ROLE_PLAYER).isPresent()) {
//            throw new IncorrectInputForNewGameException("User does not exist");
//        }
//        return true;
//    }
//
//
//}
