package br.com.demo.person.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    private Integer id;

    @NotBlank(message = "Error: Name is mandatory.")
    @Size(max = 70, message = "Error: Name should have max 70 caracters.")
    private String fullName;

    @NotNull(message = "Date birthdate is mandatory!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime birthdate;

    @NotNull(message = "Gender is mandatory!")
    private Integer idGender;
}
