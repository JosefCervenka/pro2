package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.model.Film;
import cz.uhk.pro2_d.model.Person;
import cz.uhk.pro2_d.service.Interfaces.IFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/film")
public class FilmController {

    private final IFilmService filmService;

    @Autowired
    FilmController(IFilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("films", filmService.findAll());
        return "film/index";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable Long id, Model model) {
        model.addAttribute("film", filmService.findById(id));
        return "film/detail";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("film", new Film());
        return "film/create";
    }

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

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        filmService.delete(id);
        return "redirect:/film";
    }

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
