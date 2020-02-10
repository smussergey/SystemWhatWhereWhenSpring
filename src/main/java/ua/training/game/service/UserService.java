package ua.training.game.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.training.game.domain.User;
import ua.training.game.enums.Role;
import ua.training.game.exception.EntityNotFoundException;
import ua.training.game.repository.UserRepository;
import ua.training.game.web.dto.UserRegistrationDTO;

import java.util.List;

//TODO add logger to write to file
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerNewUser(UserRegistrationDTO userRegistrationDto) {
        User user;
        user = User.builder()
                .nameUa(userRegistrationDto.getNameUa())
                .nameEn(userRegistrationDto.getNameEn())
                .email(userRegistrationDto.getEmail())
                .password(passwordEncoder.encode(userRegistrationDto.getPassword()))
                .role(Role.ROLE_PLAYER)
                .build();

        User registeredUser = save(user);
        log.info("IN register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }

    public User findUserByLogin(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + email + " was not found"));
        log.info("User with login {} is trying to log in ", user.getEmail());
        return user;
    }

    public User findUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with " + id + " was not found"));
    }

    public List<User> findAllUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    public User findLoggedIndUser() { //TODO redo using Principal
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return findUserByLogin(username);
    }
}
