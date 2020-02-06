package ua.training.systemwww.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.systemwww.entity.Question;

public interface AnsweredQuestionRepository extends JpaRepository<Question, Long> {
}
