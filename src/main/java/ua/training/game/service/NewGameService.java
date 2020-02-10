package ua.training.game.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.training.game.domain.Game;
import ua.training.game.domain.Question;
import ua.training.game.domain.User;
import ua.training.game.exception.TwoPlayersTheSameException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class NewGameService {

    private final UserService userService;
    private final GameService gameService;

    public NewGameService(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    public Game runNewGame(Long firstPlayerId, Long secondPlayerId, int maxNumberOfScoresToFinishGame) {

        if (firstPlayerId.equals(secondPlayerId)) {
            log.error("IN NewGameService, method runNewGame- firstPlayerId: {} secondPlayerId {} are the same", firstPlayerId, secondPlayerId);
            throw new TwoPlayersTheSameException("firstPlayerId and secondPlayerId are the same ");
        }

        Game game = generateNewGameWithResults(firstPlayerId, secondPlayerId, maxNumberOfScoresToFinishGame);

        return save(game);
    }

    @Transactional
    public Game save(Game game) {
        return gameService.save(game);
    }

    private Game generateNewGameWithResults(Long firstPlayerId, Long secondPlayerId, int maxNumberOfScoresToFinishGame) {

        User firstPlayer = userService.findUserById(firstPlayerId);
        User secondPlayer = userService.findUserById(secondPlayerId);

        List<Question> questions = generateQuestionsUntilMaxNumberOfScoresIsReached(firstPlayer, secondPlayer, maxNumberOfScoresToFinishGame);

        Game game = Game.builder()
                .date(LocalDate.now())
                .firstPlayer(firstPlayer)
                .secondPlayer(secondPlayer)
                .questions(new ArrayList<>())
                .build();
        game.addQuestions(questions);  // TODO check how o add to builder

        return game;
    }

    private List<Question> generateQuestionsUntilMaxNumberOfScoresIsReached(User firstPlayer, User secondPlayer,
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
