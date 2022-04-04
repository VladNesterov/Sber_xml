package com.example.sber.controller;

import com.example.sber.dto.Person;
import com.example.sber.dto.UserDto;
import com.example.sber.service.JaxbWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    JaxbWorker jaxbWorker;

    @RequestMapping(value = "save/user", method = RequestMethod.POST)
    public Person addUser(@RequestBody UserDto userDto) {
        return jaxbWorker.save(userDto);
    }

    @RequestMapping(value = "reference/team", method = RequestMethod.GET)
    public List<Person> getReferenceTeam(@RequestParam String organization) {
        return jaxbWorker.getReferenceByOrganization(organization);
    }

    @RequestMapping(value = "update/user", method = RequestMethod.POST)
    public Person changeUser(@RequestParam String id, @RequestBody UserDto userDto) {
        return jaxbWorker.updatePerson(id, userDto);
    }

    @RequestMapping(value = "remove/user", method = RequestMethod.DELETE)
    public void addUser(@RequestParam String id) {
        jaxbWorker.removePerson(id);
    }


    @RequestMapping(value = "get/persons", method = RequestMethod.GET)
    public List<Person> getUsers() {
        return jaxbWorker.getAllPersons();
    }

    @RequestMapping(value = "get/person", method = RequestMethod.GET)
    public Person getUser(@RequestParam String id) {
        return jaxbWorker.getPersonById(id);
    }
}
