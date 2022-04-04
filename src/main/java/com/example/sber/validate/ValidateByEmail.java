package com.example.sber.validate;

import com.example.sber.dto.Person;
import com.example.sber.exception.PersonException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ValidateByEmail {

    private static final String EMAIL_PATTERN = "^(.+)@(\\S+)$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public void validateEmail(String email){
       if (!isValid(email)){
           throw new PersonException("Неверный формат почты");
       }
    }
    public void validateEmail(List<Person> personList, String email){
        for (Person person : personList) {
            if (person.getEmail().equals(email)){
                throw new PersonException("Почта = " + email + " Уже существет");
            }
        }
    }

    private static boolean isValid(final String email) {
        if (email== null){
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
