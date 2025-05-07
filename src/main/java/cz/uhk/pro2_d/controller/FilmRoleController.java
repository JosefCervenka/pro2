package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.model.FilmRole;
import cz.uhk.pro2_d.service.Interfaces.IFilmRoleService;
import cz.uhk.pro2_d.service.Interfaces.IFilmService;
import cz.uhk.pro2_d.service.Interfaces.IPersonService;
import cz.uhk.pro2_d.service.service.FilmRoleService;
import cz.uhk.pro2_d.service.service.FilmService;
import cz.uhk.pro2_d.service.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/filmRole")
public class FilmRoleController {

    private final IFilmRoleService filmRoleService;

    private final IFilmService filmService;

    private final IPersonService personService;

    @Autowired
    public FilmRoleController(IFilmRoleService filmRoleService, IFilmService filmService, IPersonService personService) {
        this.filmRoleService = filmRoleService;
        this.filmService = filmService;
        this.personService = personService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public String create(Model model, @RequestParam(required = false) Long personId, @RequestParam(required = false) Long filmId) {

        var filmRole = new FilmRole();
        if(personId != null) {
            filmRole.setActor(personService.findById(personId));
        }
        if(filmId != null) {
            filmRole.setFilm(filmService.findById(filmId));
        }
        model.addAttribute("filmRole", filmRole);
        model.addAttribute("films", filmService.findAll());
        model.addAttribute("people", personService.findAll());

        return "filmRole/create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String create(@ModelAttribute FilmRole filmRole) {
        filmRoleService.save(filmRole);
        return "redirect:/film/"+filmRole.getFilm().getId();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        var role = filmRoleService.delete(id);
        return "redirect:/film/"+role.getFilm().getId();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable Long id) {
        model.addAttribute("filmRole", filmRoleService.findById(id));
        model.addAttribute("films", filmService.findAll());
        model.addAttribute("people", personService.findAll());

        return "filmRole/create";
    }
}
