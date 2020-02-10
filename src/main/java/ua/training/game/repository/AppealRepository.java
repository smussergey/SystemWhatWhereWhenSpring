package ua.training.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.game.domain.Appeal;

public interface AppealRepository extends JpaRepository<Appeal, Long> {
}