package ua.training.systemwww.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.systemwww.entity.Appeal;
import ua.training.systemwww.entity.AppealStage;

import java.util.List;

public interface AppealRepository extends JpaRepository<Appeal, Long> {
    List<Appeal> findAllByAppealStage(AppealStage appealStage);
}