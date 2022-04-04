package com.example.sber.service;

import com.example.sber.dto.Person;
import com.example.sber.dto.UserDto;
import com.example.sber.exception.PersonException;
import com.example.sber.validate.ValidateByEmail;
import com.example.sber.wrapper.PersonListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class JaxbWorker {
    @Value("${source.file}")
    private String inputFile;

    @Autowired
    ValidateByEmail validateByEmail;

    public List<Person> getReferenceByOrganization(String organization) {
        PersonListWrapper personListWrapper = fromXmlToObject(inputFile);
        if (personListWrapper == null) {
            throw new PersonException("Xml файл пустой");
        }
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < personListWrapper.getPersons().size(); i++) {

        }
        return personListWrapper.getPersons()
                .stream()
                .filter(it -> organization.equals(it.getOrganization()))
                .collect(Collectors.toList());
    }

    public void removePerson(String id) {
        PersonListWrapper personListWrapper = fromXmlToObject(inputFile);
        if (personListWrapper == null) {
            throw new PersonException("Xml файл пустой");
        }
        Person person = new Person();
        List<Person> personList = new ArrayList<>(personListWrapper.getPersons());
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getId().equals(id)) {
                personList.remove(i);
                break;
            }
        }
        personListWrapper.setPersons(personList);
        convertObjectToXml(personListWrapper, inputFile);
    }

    public List<Person> getAllPersons() {
        PersonListWrapper personListWrapper = fromXmlToObject(inputFile);
        if (personListWrapper == null) {
            throw new PersonException("Xml файл пустой");
        }
        return personListWrapper.getPersons();
    }

    public Person getPersonById(String id) {
        PersonListWrapper personListWrapper = fromXmlToObject(inputFile);
        if (personListWrapper == null) {
            throw new PersonException("Xml файл пустой");
        }
        for (int i = 0; i < personListWrapper.getPersons().size(); i++) {
            if (personListWrapper.getPersons().get(i).getId().equals(id)) {
                return personListWrapper.getPersons().get(i);
            }
        }
        return null;
    }

    public Person updatePerson(String id, UserDto userDto) {
        PersonListWrapper personListWrapper = fromXmlToObject(inputFile);
        if (personListWrapper == null) {
            throw new PersonException("Xml файл пустой");
        }
        Person person = new Person();
        List<Person> personList = new ArrayList<>(personListWrapper.getPersons());
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getId().equals(id)) {
                if (!personList.get(i).getEmail().equals(userDto.getEmail())){
                    validateByEmail.validateEmail(personListWrapper.getPersons(), userDto.getEmail());
                }
                person = convertUserDtoTopPerson(userDto);
                person.setId(id);
                personList.set(i, person);
                break;
            }
        }
        personListWrapper.setPersons(personList);
        convertObjectToXml(personListWrapper, inputFile);
        return person;
    }

    public Person save(UserDto userDto) {
        validateByEmail.validateEmail(userDto.getEmail());
        PersonListWrapper personListWrapper = fromXmlToObject(inputFile);
        Person person = new Person();
        if (personListWrapper != null) {
            List<Person> listPersons = new ArrayList<>(personListWrapper.getPersons());
            person = convertUserDtoTopPerson(userDto);
            listPersons.add(person);
            validateByEmail.validateEmail(personListWrapper.getPersons(), userDto.getEmail());
            personListWrapper.setPersons(listPersons);
            convertObjectToXml(personListWrapper, inputFile);
        } else {
            PersonListWrapper newPersonListWrapper = new PersonListWrapper();
            person = convertUserDtoTopPerson(userDto);
            newPersonListWrapper.setPersons(List.of(person));
            convertObjectToXml(newPersonListWrapper, inputFile);
        }

        return person;
    }

    private Person convertUserDtoTopPerson(UserDto userDto) {
        Person person = new Person();
        person.setId(String.valueOf(UUID.randomUUID()));
        person.setTableNumber(userDto.getTableNumber());
        person.setFirstName(userDto.getFirstName());
        person.setSurname(userDto.getSurname());
        person.setPatronymic(userDto.getPatronymic());
        person.setListPhones(userDto.getListPhones());
        person.setEmail(userDto.getEmail());
        person.setPosition(userDto.getPosition());
        person.setOrganization(userDto.getOrganization());
        return person;
    }

    // восстанавливаем объект из XML файла
    private static PersonListWrapper fromXmlToObject(String filePath) {
        try {
            // создаем объект JAXBContext - точку входа для JAXB
            JAXBContext jaxbContext = JAXBContext.newInstance(PersonListWrapper.class);
            Unmarshaller un = jaxbContext.createUnmarshaller();
            File printWriter = new File(filePath);
            return (PersonListWrapper) un.unmarshal(printWriter);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    // сохраняем объект в XML файл
    private static void convertObjectToXml(PersonListWrapper personListWrapper, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            File printWriter = new File(filePath);
            marshaller.marshal(personListWrapper, printWriter);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
