package com.example.sber.wrapper;

import com.example.sber.dto.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Вспомогательный класс для обёртывания списка адресатов.
 * Используется для сохранения списка адресатов в XML.
 *
 * @author Marco Jakob
 */
@XmlRootElement(name = "persons")
@Data
@EqualsAndHashCode
public class PersonListWrapper {

    private List<Person> persons;

    @XmlElement(name = "person")
    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

}
