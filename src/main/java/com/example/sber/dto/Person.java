package com.example.sber.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person {
    String id;
    String tableNumber;
    String firstName;
    String surname;
    String patronymic;
    List<String> listPhones;
    @NotNull
    String email;
    String position;
    String organization;
}
