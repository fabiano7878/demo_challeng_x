package br.com.demo.person.funcional;

import br.com.demo.person.dto.PersonDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Set;

@FunctionalInterface
public interface ValidateFuncional<T> {
    boolean isIdNotNull(T t);

    default boolean isNullOrBlankMandatoryies(PersonDTO personDTO, Validator validator) {
        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(personDTO);
        StringBuilder sb = new StringBuilder();
        if (!CollectionUtils.isEmpty(violations)) {
            for (ConstraintViolation<PersonDTO> violation : violations) {
                sb.append(violation.getMessage()).append("\n");
                System.out.println(sb);
                return true;
            }
        }
        return false;
    }

    default boolean isAllAtributtesNull(PersonDTO personDTO){
        if(Objects.nonNull(personDTO) && !StringUtils.hasText(personDTO.getFullName()) && Objects.isNull(personDTO.getBirthdate()) && Objects.isNull(personDTO.getIdGender())) return true;
        return false;
    }
}
