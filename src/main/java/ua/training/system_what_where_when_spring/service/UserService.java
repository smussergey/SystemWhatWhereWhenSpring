package ua.training.system_what_where_when_spring.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.training.system_what_where_when_spring.exception.EntityNotFoundException;
import ua.training.system_what_where_when_spring.entity.Role;
import ua.training.system_what_where_when_spring.entity.User;
import ua.training.system_what_where_when_spring.repository.UserRepository;

import java.util.List;
import java.util.Optional;

//TODO add logger to write to file
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User findUserByLogin(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + email + " was not found"));
        log.info("User with login {} is trying to log in ", user.getEmail());
        return user;
    }

    public User findUserById(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with " + id + " was not found"));
        return user;
    }

    public Optional<User> findUserByIdOptional(Long id) {
        return userRepository.findById(id);
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = findUserByEmail(email);
//
//        if (user == null) {
//            throw new UsernameNotFoundException("User with username: " + email + " not found");
//        }
//
//        log.info("IN loadUserByUsername - user with username: {} successfully loaded", email);
//        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.singleton(user.getRole()));
//    }

    public List<User> findAllUsersByRole(Role role) {
        return userRepository.findByRole(role).orElseThrow(() -> new EntityNotFoundException("Can not fond users with role: " + role.name()));
    }

    public User findLoggedIndUser() {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return findUserByLogin(username);
    }
}
