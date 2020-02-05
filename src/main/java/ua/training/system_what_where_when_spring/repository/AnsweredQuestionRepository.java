package ua.training.system_what_where_when_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.system_what_where_when_spring.entity.AnsweredQuestion;

public interface AnsweredQuestionRepository extends JpaRepository<AnsweredQuestion, Long> {
}
