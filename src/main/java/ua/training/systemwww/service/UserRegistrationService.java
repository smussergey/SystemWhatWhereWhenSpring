package ua.training.systemwww.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.training.systemwww.dto.UserRegistrationDTO;
import ua.training.systemwww.entity.Role;
import ua.training.systemwww.entity.User;
import ua.training.systemwww.repository.UserRepository;

@Slf4j
@Service
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserRegistrationService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
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

        User registeredUser = userRepository.save(user);
        log.info("IN register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }
}
