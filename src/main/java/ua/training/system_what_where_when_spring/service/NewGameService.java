package ua.training.system_what_where_when_spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.training.system_what_where_when_spring.entity.Question;
import ua.training.system_what_where_when_spring.entity.Game;
import ua.training.system_what_where_when_spring.entity.User;
import ua.training.system_what_where_when_spring.exception.TwoPlayersTheSameException;
import ua.training.system_what_where_when_spring.repository.GameRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class NewGameService {

    private final UserService userService;
    private final GameRepository gameRepository;

    public NewGameService(UserService userService, GameRepository gameRepository) {
        this.userService = userService;
        this.gameRepository = gameRepository;
    }

    public Game runNewGame(Long firstPlayerId, Long secondPlayerId, int maxNumberOfScoresToFinishGame) {

        if (firstPlayerId == secondPlayerId) {
            log.error("IN NewGameService, method runNewGame- firstPlayerId: {} secondPlayerId {} are the same", firstPlayerId, secondPlayerId);
            throw new TwoPlayersTheSameException("firstPlayerId and secondPlayerId are the same ");
        }

        Game game = generateNewGameWithResults(firstPlayerId, secondPlayerId, maxNumberOfScoresToFinishGame);

        return save(game);
    }

    @Transactional
    public Game save(Game game) {
        return gameRepository.save(game);
    }

    private Game generateNewGameWithResults(Long firstPlayerId, Long secondPlayerId, int maxNumberOfScoresToFinishGame) {

        User firstPlayer = userService.findUserById(firstPlayerId);
        User secondPlayer = userService.findUserById(secondPlayerId);

        List<Question> questions = generateQuestionsUntillMaxNumberOfScoresIsReached(firstPlayer, secondPlayer, maxNumberOfScoresToFinishGame);

        Game game = new Game();
        game.setDate(LocalDate.now());
        game.setFirstPlayer(firstPlayer);
        game.setSecondPlayer(secondPlayer);
//        System.out.println("------------------------" + game.getFirstPlayer());
//        System.out.println("------------------------" + game.getQuestions().size());
        game.addQuestions(questions);

        return game;
    }

    private List<Question> generateQuestionsUntillMaxNumberOfScoresIsReached(User firstPlayer, User secondPlayer,
                                                                             int maxNumberOfScoresToFinishGame) {
        int firstPlayerScoresCount = 0;
        int secondPlayerScoresCount = 0;
        List<Question> questions = new ArrayList<>();

        while (true) {
            Question question = generateQuestion(firstPlayer, secondPlayer);
            questions.add(question);

            if (firstPlayer.equals(question.getUserWhoGotPoint())) {
                firstPlayerScoresCount++;
            } else {
                secondPlayerScoresCount++;
            }

            if (firstPlayerScoresCount == maxNumberOfScoresToFinishGame
                    || secondPlayerScoresCount == maxNumberOfScoresToFinishGame) {
                break;
            }
        }
        return questions;
    }

    private Question generateQuestion(User firstPlayer, User secondPlayer) {
        Question question = new Question();
        if (ThreadLocalRandom.current().nextBoolean()) {
            question.setUserWhoGotPoint(firstPlayer);
        } else {
            question.setUserWhoGotPoint(secondPlayer);
        }
        return question;
    }
}
