package br.com.demo.person.service;

import br.com.demo.person.builder.PersonBuilder;
import br.com.demo.person.dto.PersonDTO;
import br.com.demo.person.funcional.ValidateFuncional;
import br.com.demo.person.indicator.TypeIndicatorGender;
import br.com.demo.person.model.Gender;
import br.com.demo.person.model.Person;
import br.com.demo.person.record.PersonRecord;
import br.com.demo.person.repository.PersonRepository;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonBuilder personBuilder;

    @InjectMocks
    private PersonService personService;

    @Mock
    private Validator validator;

    @Mock
    ValidateFuncional validateFuncional;

    @Test
    void consultAllReturnPersonRecord_whenPersonsExist() {
        List<Person> persons = List.of(
                Person.builder().id(1).fullName("John Silva").birthdate(LocalDateTime.now().minusYears(30)).build()
        );
        Page<Person> personPage = new PageImpl<>(persons);
        Pageable pageable = PageRequest.of(0, 10);
        Mockito.when(personRepository.findAll(pageable)).thenReturn(personPage);
        Mockito.when(personBuilder.getAllPerson(persons)).thenReturn(new LinkedHashSet<>(Set.of(
                new PersonRecord(1, "John Silva", LocalDateTime.now().minusYears(30), TypeIndicatorGender.MALE)
        )));

        Page<PersonRecord> result = personService.getListPerson(pageable);
        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        Mockito.verify(personRepository).findAll(pageable);
        Mockito.verify(personBuilder).getAllPerson(persons);
    }

    @Test
    void consultAllReturnEmptyPage_whenNoPersonsExist() {
        Page<Person> emptyPage = Page.empty();
        Pageable pageable = PageRequest.of(0, 10);
        Mockito.when(personRepository.findAll(pageable)).thenReturn(emptyPage);

        Page<PersonRecord> result = personService.getListPerson(pageable);
        assertTrue(result.isEmpty());
        Mockito.verify(personRepository).findAll(pageable);
        Mockito.verify(personBuilder, Mockito.never()).getAllPerson(Mockito.anyList());
    }

    @Test
    void notCreatePerson_whenDTOIsInvalid() {
        PersonDTO invalidPersonDTO = new PersonDTO(null, null, null, null);
        PersonRecord result = personService.createPerson(v -> Objects.nonNull(v), invalidPersonDTO);

        assertNull(result);
        Mockito.verify(personRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void createNewPerson_whenDTOIsValid() {
        PersonDTO validPersonDTO = new PersonDTO(null, "John Silva", LocalDateTime.now().minusYears(30), 1);
        Person person = Person.builder().id(1).fullName("John Silva").birthdate(LocalDateTime.now().minusYears(30)).build();
        PersonRecord personRecord = new PersonRecord(1, "John Silva", LocalDateTime.now().minusYears(30), TypeIndicatorGender.MALE);

        Mockito.when(personBuilder.createNewPerson(validPersonDTO)).thenReturn(person);
        Mockito.when(personRepository.save(person)).thenReturn(person);
        Mockito.when(personBuilder.createNewPersonRecord(person)).thenReturn(personRecord);

        PersonRecord result = personService.createPerson(v -> Objects.nonNull(v), validPersonDTO);
        assertNotNull(result);
        assertEquals(personRecord, result);
        Mockito.verify(personRepository).save(person);
    }

    @Test
    void updatePerson_whenIdIsValidAndAttributes() {
        // Arrange
        Integer validId = 1;
        PersonDTO validPayload = new PersonDTO(1, "Mary Updated", LocalDateTime.now().minusYears(25), 2);
        Person existingPerson = Person.builder().id(validId).fullName("Mary Silva").birthdate(LocalDateTime.now().minusYears(30)).gender(Gender.builder().id(2).name("FEMALE").build()).build();
        Person updatedPerson = Person.builder().id(validId).fullName("Mary Updated").birthdate(LocalDateTime.now().minusYears(25)).gender(Gender.builder().id(2).name("FEMALE").build()).build();
        PersonRecord expectedPersonRecord = new PersonRecord(validId, "Mary Updated", LocalDateTime.now().minusYears(25), TypeIndicatorGender.FEMALE);

        Mockito.when(personRepository.findById(validId)).thenReturn(Optional.of(existingPerson));
        Mockito.when(personRepository.save(Mockito.any(Person.class))).thenReturn(updatedPerson);
        Mockito.when(personBuilder.changePerson(existingPerson, validPayload)).thenReturn(updatedPerson);
        Mockito.when(personBuilder.buildPersonUpdate(updatedPerson)).thenReturn(expectedPersonRecord);


        PersonRecord result = personService.updatePerson(validId, v -> Objects.nonNull(v), validPayload);
        assertNotNull(result);
        assertEquals(expectedPersonRecord, result);
        Mockito.verify(personRepository).save(Mockito.any(Person.class));
    }

    @Test
    void inProcessUpdateReturnNull_whenPersonIdDoesNotExist() {
        Integer invalidId = 99;
        PersonDTO validPayload = new PersonDTO(1, "John Updated", LocalDateTime.now().minusYears(25), 1);

        Mockito.when(personRepository.findById(invalidId)).thenReturn(Optional.empty());
        PersonRecord result = personService.updatePerson(invalidId, v -> Objects.nonNull(v), validPayload);
        assertNull(result);
        Mockito.verify(personRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void removePerson_whenIdIsValid() {
        // Arrange
        Integer validId = 1;
        Person existingPerson = Person.builder()
                .id(validId)
                .fullName("Oswald Silva")
                .birthdate(LocalDateTime.now().minusYears(30))
                .gender(Gender.builder().id(1).name("Male").build())
                .build();
        PersonRecord expectedPersonRecord = new PersonRecord(validId, "Oswald Silva", existingPerson.getBirthdate(), TypeIndicatorGender.MALE);


        Mockito.when(personRepository.findById(validId)).thenReturn(Optional.of(existingPerson));
        Mockito.doNothing().when(personRepository).deleteById(validId);
        PersonRecord result = personService.removePerson(validId, v -> Objects.nonNull(v));
        assertNotNull(result);
        assertEquals(expectedPersonRecord, result);
        Mockito.verify(personRepository).deleteById(validId);
    }

    @Test
    void notRemovePerson_whenPersonDoesNotExist() {
        Integer invalidId = 999;
        Mockito.when(personRepository.findById(invalidId)).thenReturn(Optional.empty());
        Mockito.when(validateFuncional.isIdNotNull(invalidId)).thenReturn(true);
        PersonRecord result = personService.removePerson(invalidId, validateFuncional);
        assertNull(result);
        Mockito.verify(personRepository, Mockito.never()).deleteById(invalidId);
    }

    @Test
    void notRemovePerson_whenIdIsNull() {
        Integer nullId = null;
        Mockito.when(validateFuncional.isIdNotNull(nullId)).thenReturn(false);

        PersonRecord result = personService.removePerson(nullId, validateFuncional);
        assertNull(result);
        Mockito.verify(personRepository, Mockito.never()).deleteById(nullId);
    }
}