package cz.uhk.pro2_d.controller;


import cz.uhk.pro2_d.model.Person;
import cz.uhk.pro2_d.service.Interfaces.IFilmRoleService;
import cz.uhk.pro2_d.service.Interfaces.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Controller
@RequestMapping("/person")
public class PersonController {

    private final IPersonService personService;

    private final IFilmRoleService filmRoleService;

    @Autowired
    PersonController(IPersonService personService, IFilmRoleService filmRoleService) {
        this.personService = personService;
        this.filmRoleService = filmRoleService;
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("person", new Person());
        return "person/create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String create(@ModelAttribute Person person, @RequestParam("file") MultipartFile file) {
        try {
            var data = file.getBytes();

            if(data.length > 0) {
                person.setPhoto(data);
            }
            else{
                person.setPhoto(personService.findById(person.getId()).getPhoto());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/person/create";
        }

        var newPerson = personService.save(person);
        return "redirect:/person/" + newPerson.getId();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("delete/{id}")
    public String delete(@PathVariable Long id) {
        var person = personService.findById(id);

        for (var role : person.getFilmRoles()) {
            filmRoleService.delete(role.getId());
        }

        personService.delete(id);
        return "redirect:/person";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable Long id) {
        model.addAttribute("person", personService.findById(id));
        return "person/create";
    }

    @GetMapping("/photo/{id}")
    public @ResponseBody byte[] photo(@PathVariable Long id)
    {
        return personService.findById(id).getPhoto();
    }
}
