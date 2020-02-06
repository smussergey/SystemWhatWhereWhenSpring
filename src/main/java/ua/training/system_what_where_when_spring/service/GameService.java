//package ua.training.system_what_where_when_spring.service;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import ua.training.system_what_where_when_spring.repository.GameRepository;
//
//@Service
//public class GameService {
//    private final GameRepository gameRepository;
//
//    public GameService(GameRepository gameRepository) {
//        this.gameRepository = gameRepository;
//    }
//
//    @Transactional
//    public void deleteGameById(Long id) {
//        gameRepository.deleteById(id);
//    }
//}
