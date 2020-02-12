package ua.training.game.web.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserRegistrationDTO {
    private static String REGEX_NAME_UA = "^[А-ЩЬЮЯҐІЇЄ]{1}[а-щьюяґіїє']{1,}$"; //TODO move to properties
    private static String REGEX_NAME_EN = "^[A-Z]{1}[a-z]{1,}$";//TODO move to properties
//    @NotBlank(message = "validation.name.ua.cannot.be.blank")
    @Pattern(regexp = "^[А-ЩЬЮЯҐІЇЄ]{1}[а-щьюяґіїє']{1,}$", message = "validation.name.ua.pattern")
    private String nameUa;
//    @NotBlank(message = "validation.name.en.cannot.be.blank")
    @Pattern(regexp = "^[A-Z]{1}[a-z]{1,}$", message = "validation.name.en.pattern")
    private String nameEn;
    @NotBlank(message = "validation.email.cannot.be.blank")
    // @Email (message = "validation.email.pattern") //TODO uncomment in final project and add error message
    private String email;
    @NotBlank(message = "validation.password.cannot.be.blank")
    private String password;
}
