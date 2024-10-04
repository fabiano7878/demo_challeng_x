package br.com.demo.person.controller;

import br.com.demo.person.dto.PersonDTO;
import br.com.demo.person.record.PersonRecord;
import br.com.demo.person.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Controller
@RequestMapping("/person")
public class PersonController {


    @Autowired
    PersonService personService;

    @GetMapping
    public String homePerson(Model model) {
        return "index";
    }

    @GetMapping("/toRemove")
    public String goToRemove(Model model) {
        return "removed";
    }

    @GetMapping("/toUpdate")
    public String goToUpdate(Model model) {
        return "update";
    }

    @GetMapping("/toCreate")
    public String goToCreate(Model model) {
        return "create";
    }

    @GetMapping("/toSearch")
    public String goToSearch(Model model) {
        return "search";
    }

    @GetMapping("/list")
    public String getListPerson(Model model,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PersonRecord> personsPage = personService.getListPerson(pageable);
        model.addAttribute("persons", personsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", personsPage.getTotalPages());
        return "list";
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public PersonRecord createPerson(@RequestBody PersonDTO payloadPersonDTO) {
        return personService.createPerson((v) -> Objects.nonNull(v), payloadPersonDTO);
    }

    @PatchMapping(value="/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public PersonRecord updatePerson(@PathVariable("id") Integer id, @RequestBody PersonDTO payLoadToUpdate) {
        return personService.updatePerson(id, (v) -> Objects.nonNull(v), payLoadToUpdate);
    }

    @DeleteMapping(value="/remove/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public PersonRecord removePerson(@PathVariable("id") Integer id) {
        return personService.removePerson(id, (v) -> Objects.nonNull(v));
    }

    @GetMapping("/search")
    @ResponseBody
    public PersonRecord findPerson(@RequestParam(required = false) String fullName,
                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime birthdate,
                             @RequestParam(required = false) Integer idGender) {
        return personService.findPerson(fullName, birthdate, idGender);
    }
}
