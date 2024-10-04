package br.com.demo.person.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_PERSON")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_GENDER", nullable = false)
    private Gender gender;


    @Column(nullable = false)
    private String fullName;


    @Past
    @Column(name = "BIRTHDATE" ,nullable = false)
    private LocalDateTime birthdate;
}
