package ua.training.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.game.domain.History;

public interface HistoryRepository extends JpaRepository<History, Long> {
}