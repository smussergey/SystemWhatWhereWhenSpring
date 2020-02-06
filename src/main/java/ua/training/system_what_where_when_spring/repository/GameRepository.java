package ua.training.system_what_where_when_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.system_what_where_when_spring.entity.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
//    Page<Game> findAll(Pageable pageable);

//    Page<Game> findAllByUsers(User user, Pageable pageable);
}