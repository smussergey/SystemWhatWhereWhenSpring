package ua.training.systemwww.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.systemwww.entity.Game;
import ua.training.systemwww.entity.User;

public interface GameRepository extends JpaRepository<Game, Long> {
//    Page<Game> findAll(Pageable pageable);

//    Page<Game> findAllByUsers(User user, Pageable pageable);
}