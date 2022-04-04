package com.example.sber.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    String tableNumber;
    String firstName;
    String surname;
    String patronymic;
    List<String> listPhones;
    String email;
    String position;
    String organization;
}
