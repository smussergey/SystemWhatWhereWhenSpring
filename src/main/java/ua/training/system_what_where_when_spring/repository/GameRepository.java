package ua.training.system_what_where_when_spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.system_what_where_when_spring.entity.Game;
import ua.training.system_what_where_when_spring.entity.User;

import java.time.LocalDate;

public interface GameRepository extends JpaRepository<Game, Long> {
    Page<Game> findAllByFirstPlayerOrSecondPlayer(User firstPlayer, User secondPlayer, Pageable pageable);

    Page<Game> findAllByDateBefore(LocalDate localDate, Pageable pageable);
}