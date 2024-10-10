package br.com.demo.person.indicator;

import java.util.Arrays;

public enum TypeIndicatorRole {

    ADMIN(1, "ADMIN"),
    USER(2,  "USER"),
    DEVELOPER(3, "DEVELOPER"),
    SYSTEM(4, "SYSTEM"),
    ;

    private Integer id;

    private String value;

    TypeIndicatorRole(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public static TypeIndicatorRole getTypeIndicatorRole(String value) {
        return Arrays.stream(TypeIndicatorRole.values()).filter(i -> value.equals(i.value)).findFirst().get();
    }
}
