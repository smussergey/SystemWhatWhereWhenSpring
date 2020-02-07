package ua.training.system_what_where_when_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.system_what_where_when_spring.entity.Appeal;
import ua.training.system_what_where_when_spring.entity.AppealStage;

import java.util.List;

public interface AppealRepository extends JpaRepository<Appeal, Long> {
}