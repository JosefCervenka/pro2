package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.model.Comment;
import cz.uhk.pro2_d.model.FilmRole;
import cz.uhk.pro2_d.security.MyUserDetails;
import cz.uhk.pro2_d.service.Interfaces.IFilmRoleService;
import cz.uhk.pro2_d.service.Interfaces.IFilmService;
import cz.uhk.pro2_d.service.Interfaces.IPersonService;
import cz.uhk.pro2_d.service.Interfaces.IUserService;
import cz.uhk.pro2_d.service.service.CommentService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private final IFilmService filmService;
    private final CommentService commentService;

    @Autowired
    public CommentController(IFilmService filmService, IUserService userService, CommentService commentService) {
        this.filmService = filmService;
        this.commentService = commentService;
    }

    @GetMapping("/create")
    public String create(Model model, @RequestParam Long filmId, @AuthenticationPrincipal MyUserDetails userDetails) {
        var comment = new Comment();
        var film = filmService.findById(filmId);
        comment.setFilm(film);

        model.addAttribute("comment", comment);
        return "comment/create";
    }


    @PostMapping("/create")
    public String create(@ModelAttribute Comment comment, @AuthenticationPrincipal MyUserDetails userDetails) {
        comment.setAuthor(userDetails.getUser());
        commentService.save(comment);
        return "redirect:/film/"+comment.getFilm().getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        var comment = commentService.delete(id);
        return "redirect:/film/"+comment.getFilm().getId();
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable Long id) {
        model.addAttribute("comment", commentService.findById(id));

        return "comment/create";
    }
}
