package br.com.demo.person.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisterDTO {

    @NotBlank(message = "Error: login is mandatory, e precisa ter o formato de email exemplo: nome@email.com")
    @Email
    private String login;

    @NotBlank(message = "Error: passwrod is mandatory.")
    private String password;

    @NotBlank(message = "Error: role is mandatory.")
    private String role;
}
