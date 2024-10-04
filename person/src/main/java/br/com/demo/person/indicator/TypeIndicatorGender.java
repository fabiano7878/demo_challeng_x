package br.com.demo.person.indicator;

import java.util.Arrays;

public enum TypeIndicatorGender {
    MALE(1, "MALE"),
    FEMALE(2, "FEMALE"),
    ;

    private final Integer idGender;
    private final String value;

    TypeIndicatorGender(Integer idGender, String value) {
        this.idGender = idGender;
        this.value = value;
    }

    public Integer getIdGender() {
        return idGender;
    }

    public String getValue() {
        return value;
    }

    public static TypeIndicatorGender getGender(Integer id) {
        return Arrays.stream(TypeIndicatorGender.values()).filter(i -> id.equals(i.idGender)).findFirst().get();
    }
}
