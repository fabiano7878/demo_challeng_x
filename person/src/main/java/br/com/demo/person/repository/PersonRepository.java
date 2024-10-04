package br.com.demo.person.repository;

import br.com.demo.person.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Page<Person> findAll(Pageable pageable);

    @Query(" SELECT p FROM Person p " +
            " WHERE " +
            " (:fullName IS NULL OR p.fullName = :fullName) " +
            "AND (:birthdate IS NULL OR p.birthdate = :birthdate) " +
            "AND (:idGender IS NULL OR p.gender.id = :idGender )" +
            "ORDER BY p.id ")
    Optional<Person> findPerson(@Param("fullName") String fullName,
                               @Param("birthdate") LocalDateTime birthdate,
                               @Param("idGender") Integer idGender);
}

