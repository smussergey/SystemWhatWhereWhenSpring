package ua.training.system_what_where_when_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.system_what_where_when_spring.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
