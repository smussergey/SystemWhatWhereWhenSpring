package ua.training.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.game.domain.User;
import ua.training.game.enums.Role;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    Optional<User> findById(Long id);

    Optional<User> findByIdAndRole(Long id, Role role);

    Optional<User> findByEmail(String email);

    List<User> findByRole(Role role);
}
