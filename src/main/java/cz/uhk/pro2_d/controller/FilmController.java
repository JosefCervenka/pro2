package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.model.Film;
import cz.uhk.pro2_d.model.Person;
import cz.uhk.pro2_d.security.MyUserDetails;
import cz.uhk.pro2_d.service.Interfaces.IFilmRoleService;
import cz.uhk.pro2_d.service.Interfaces.IFilmService;
import cz.uhk.pro2_d.service.Interfaces.IPersonService;
import cz.uhk.pro2_d.service.service.FilmRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/film")
public class FilmController {

    private final IFilmService filmService;

    private final IFilmRoleService filmRoleService;

    @Autowired
    FilmController(IFilmService filmService, IFilmRoleService filmRoleService) {
        this.filmService = filmService;
        this.filmRoleService = filmRoleService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("films", filmService.findAll());
        return "film/index";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable Long id, Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
        var film = filmService.findById(id);
        var currentUser = userDetails.getUser();
        model.addAttribute("film", film);
        model.addAttribute("currentUser", currentUser);

        return "film/detail";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("film", new Film());
        return "film/create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String create(@ModelAttribute Film film, @RequestParam("file") MultipartFile file) {
        try {
            var data = file.getBytes();

            if(data.length > 0) {
                film.setPhoto(data);
            }
            else{
                film.setPhoto(filmService.findById(film.getId()).getPhoto());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/film/create";
        }

        var newFilm = filmService.save(film);
        return "redirect:/film/" + newFilm.getId();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        var film = filmService.findById(id);

        for (var role : film.getFilmRoles()) {
            filmRoleService.delete(role.getId());
        }

        filmService.delete(id);
        return "redirect:/film";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable Long id) {
        model.addAttribute("film", filmService.findById(id));
        return "film/create";
    }

    @GetMapping("/photo/{id}")
    public @ResponseBody byte[] photo(@PathVariable Long id)
    {
        return filmService.findById(id).getPhoto();
    }
}
