package cz.uhk.pro2_d.controller;


import cz.uhk.pro2_d.model.Person;
import cz.uhk.pro2_d.service.Interfaces.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/person")
public class PersonController {

    private final IPersonService personService;

    @Autowired
    PersonController(IPersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("people", personService.findAll());
        return "person/index";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable Long id, Model model) {
        model.addAttribute("person", personService.findById(id));
        return "person/detail";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("person", new Person());
        return "person/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Person person) {
        var newPerson = personService.save(person);
        return "redirect:/person/" + newPerson.getId();
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable Long id) {
        personService.delete(id);
        return "redirect:/person";
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable Long id) {
        model.addAttribute("person", personService.findById(id));
        return "person/create";
    }
}
