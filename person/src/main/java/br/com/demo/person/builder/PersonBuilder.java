package br.com.demo.person.builder;

import br.com.demo.person.dto.PersonDTO;
import br.com.demo.person.indicator.TypeIndicatorGender;
import br.com.demo.person.model.Gender;
import br.com.demo.person.model.Person;
import br.com.demo.person.record.PersonRecord;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class PersonBuilder {
    public SequencedSet<PersonRecord> getAllPerson(List<Person> persons) {
        return persons.stream()
                .map(p -> new PersonRecord(p.getId(), p.getFullName(), p.getBirthdate(), TypeIndicatorGender.getGender(p.getGender().getId())))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public PersonRecord buildPersonUpdate(Person personUpdated) {
        return new PersonRecord(personUpdated.getId(), personUpdated.getFullName(), personUpdated.getBirthdate(), TypeIndicatorGender.getGender(personUpdated.getGender().getId()));
    }

    public Person changePerson(Person person, PersonDTO payLoadToUpdate) {
        person.setFullName(StringUtils.hasText(payLoadToUpdate.getFullName()) ? payLoadToUpdate.getFullName() : person.getFullName());
        person.setBirthdate(Objects.nonNull(payLoadToUpdate.getBirthdate()) ? payLoadToUpdate.getBirthdate() : person.getBirthdate());
        person.setGender(Objects.nonNull(payLoadToUpdate.getIdGender()) ? getPersonGender(payLoadToUpdate.getIdGender()) : person.getGender());
        return person;
    }

    private Gender getPersonGender(Integer idGender) {
        TypeIndicatorGender typeIndicatorGender = TypeIndicatorGender.getGender(idGender);
        return Gender.builder()
                .id(typeIndicatorGender.getIdGender())
                .name(typeIndicatorGender.getValue())
                .build();
    }

    public Person createNewPerson(PersonDTO payloadPersonDTO) {
        TypeIndicatorGender typeIndicatorGender = TypeIndicatorGender.getGender(payloadPersonDTO.getIdGender());
        return Person.builder()
                .fullName(payloadPersonDTO.getFullName())
                .birthdate(payloadPersonDTO.getBirthdate())
                .gender(getPersonGender(payloadPersonDTO.getIdGender()))
                .build();
    }

    public PersonRecord createNewPersonRecord(Person savePerson) {
        return PersonRecord.createPersonRecord(new PersonRecord(savePerson.getId(), savePerson.getFullName(), savePerson.getBirthdate(), TypeIndicatorGender.getGender(savePerson.getGender().getId())));
    }
}
