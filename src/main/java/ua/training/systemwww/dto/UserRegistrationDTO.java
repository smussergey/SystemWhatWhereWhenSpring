package ua.training.systemwww.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegistrationDTO {
    @NotBlank(message = "validation.name.ua.cannot.be.blank")
    private String nameUa;
    @NotBlank(message = "validation.name.en.cannot.be.blank")
    private String nameEn;
    // @Email //TODO uncomment in final project and add error message
    @NotBlank(message = "validation.email.cannot.be.blank")
    private String email;
    @NotBlank(message = "validation.password.cannot.be.blank")
    private String password;
}
