package ua.training.game.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.game.domain.Game;
import ua.training.game.domain.User;

import java.time.LocalDate;

public interface GameRepository extends JpaRepository<Game, Long> {
    Page<Game> findAllByFirstPlayerOrSecondPlayer(User firstPlayer, User secondPlayer, Pageable pageable);

    Page<Game> findAllByDateBefore(LocalDate localDate, Pageable pageable);
}