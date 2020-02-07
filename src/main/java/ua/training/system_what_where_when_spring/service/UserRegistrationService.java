package ua.training.system_what_where_when_spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.training.system_what_where_when_spring.dto.UserRegistrationDTO;
import ua.training.system_what_where_when_spring.entity.Role;
import ua.training.system_what_where_when_spring.entity.User;
import ua.training.system_what_where_when_spring.repository.UserRepository;

@Slf4j
@Service
public class UserRegistrationService {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserRegistrationService(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
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

        User registeredUser = userService.save(user);
        log.info("IN register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }
}
