package br.com.demo.person.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "TB_GENDER")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_GENDER")
    private Integer id;

    @NotNull
    @Size(max = 10)
    @Column(name = "NAME")
    private String name;
}
