package br.com.demo.person.record;

import br.com.demo.person.indicator.TypeIndicatorGender;

import java.time.LocalDateTime;
import java.util.Objects;

public record PersonRecord(Integer id, String fullName, LocalDateTime birthdate, TypeIndicatorGender gender) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonRecord)) return false;
        PersonRecord that = (PersonRecord) o;
        return fullName != null && fullName.equals(that.fullName); // comparando apenas pelo nome
    }

    @Override
    public int hashCode() {
        return fullName != null ? fullName.hashCode() : 0; // hash baseado no nome
    }

    public static PersonRecord createPersonRecord(PersonRecord personRecord){
        Objects.requireNonNull(personRecord.id);
        Objects.requireNonNull(personRecord.fullName);
        Objects.requireNonNull(personRecord.birthdate);
        Objects.requireNonNull(personRecord.gender);
        return personRecord;
    }
}
