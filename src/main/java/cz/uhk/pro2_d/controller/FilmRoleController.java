package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.model.FilmRole;
import cz.uhk.pro2_d.service.service.FilmRoleService;
import cz.uhk.pro2_d.service.service.FilmService;
import cz.uhk.pro2_d.service.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/filmRole")
public class FilmRoleController {

    private final FilmRoleService filmRoleService;

    private final FilmService filmService;

    private final PersonService personService;

    @Autowired
    public FilmRoleController(FilmRoleService filmRoleService, FilmService filmService, PersonService personService) {
        this.filmRoleService = filmRoleService;
        this.filmService = filmService;
        this.personService = personService;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("filmRole", new FilmRole());
        model.addAttribute("films", filmService.findAll());
        model.addAttribute("people", personService.findAll());

        return "filmRole/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute FilmRole filmRole) {
        filmRoleService.save(filmRole);
        return "redirect:/film/"+filmRole.getFilm().getId();
    }


    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        var role = filmRoleService.delete(id);
        return "redirect:/film/"+role.getFilm().getId();
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable Long id) {
        model.addAttribute("film", filmRoleService.findById(id));
        model.addAttribute("films", filmService.findAll());
        model.addAttribute("people", personService.findAll());

        return "filmRole/create";
    }
}
