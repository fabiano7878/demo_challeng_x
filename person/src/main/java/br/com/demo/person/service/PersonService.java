package br.com.demo.person.service;

import br.com.demo.person.builder.PersonBuilder;
import br.com.demo.person.dto.PersonDTO;
import br.com.demo.person.funcional.ValidateFuncional;
import br.com.demo.person.indicator.TypeIndicatorGender;
import br.com.demo.person.model.Person;
import br.com.demo.person.record.PersonRecord;
import br.com.demo.person.repository.PersonRepository;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PersonService {


    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonBuilder personBuilder;

    @Autowired
    Validator validator;

    public Page<PersonRecord> getListPerson(Pageable pageable) {
        Page<Person> personPage = personRepository.findAll(pageable);
        List<Person> persons = personPage.getContent();
        if (!CollectionUtils.isEmpty(persons)) {
            return new PageImpl<>(new ArrayList<>(personBuilder.getAllPerson(persons)), pageable, personPage.getTotalElements());
        }
        return Page.empty();
    }

    public PersonRecord createPerson(ValidateFuncional<PersonDTO> validateFuncional, PersonDTO payloadPersonDTO) {
        PersonRecord personRecord = null;
        if (!validateFuncional.isAllAtributtesNull(payloadPersonDTO) && !validateFuncional.isNullOrBlankMandatoryies(payloadPersonDTO, validator)){
            Person person = personBuilder.createNewPerson(payloadPersonDTO);
            personRecord = personBuilder.createNewPersonRecord(personRepository.save(person));
            return personRecord;
        }
            return personRecord;
    }


    public PersonRecord updatePerson(Integer id, ValidateFuncional<Integer> validateFuncional, PersonDTO payLoadToUpdate) {
        PersonRecord personRecord = null;
        if (validateFuncional.isIdNotNull(id) && !validateFuncional.isAllAtributtesNull(payLoadToUpdate)) {
            Optional<Person> personOp = personRepository.findById(id);
            if (personOp.isPresent()) {
                Person personUpdated = personBuilder.changePerson(personOp.get(), payLoadToUpdate);
                personRepository.save(personUpdated);
                personRecord = personBuilder.buildPersonUpdate(personUpdated);
                return personRecord;
            }
        }
        return personRecord;
    }

    public PersonRecord removePerson(Integer id, ValidateFuncional<Integer> validateFuncional) {
        PersonRecord personRecord = null;
        if (validateFuncional.isIdNotNull(id)) {
            Optional<Person> person = personRepository.findById(id);
            if (person.isPresent()) {
                personRecord = new PersonRecord(person.get().getId(), person.get().getFullName(), person.get().getBirthdate(), TypeIndicatorGender.getGender(person.get().getGender().getId()));
                personRepository.deleteById(id);
            }
        }
        return personRecord;
    }

    public PersonRecord findPerson(String name, LocalDateTime birthdate, Integer idGender) {
        Optional<Person> person = personRepository.findPerson(name, birthdate, idGender);
        PersonRecord personRecord = null;
        if(person.isPresent()) {
            personRecord = new PersonRecord(person.get().getId(), person.get().getFullName(), person.get().getBirthdate(), TypeIndicatorGender.getGender(person.get().getGender().getId()));
        }
        return personRecord;
    }
}


