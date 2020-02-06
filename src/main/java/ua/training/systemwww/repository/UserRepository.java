package ua.training.systemwww.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.systemwww.entity.Role;
import ua.training.systemwww.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<List<User>> findByRole(Role role); // TODO check if Optional is really needed
}
