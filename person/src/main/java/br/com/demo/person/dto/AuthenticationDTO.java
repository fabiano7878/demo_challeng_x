package br.com.demo.person.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthenticationDTO {

    @NotBlank(message = "Error: login is mandatory.")
    @Email
    private String login;

    @NotBlank(message = "Error: password is mandatory.")
    private String password;
}
