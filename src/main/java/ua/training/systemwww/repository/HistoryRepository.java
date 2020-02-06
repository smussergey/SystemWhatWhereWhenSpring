package ua.training.systemwww.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.systemwww.entity.History;

public interface HistoryRepository extends JpaRepository<History, Long> {
    Page<History> findAll(Pageable pageable);
}