package ua.training.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.game.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
