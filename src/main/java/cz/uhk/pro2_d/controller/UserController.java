package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.model.User;
import cz.uhk.pro2_d.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users_list";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable long id) {
        model.addAttribute("user", userService.getUser(id));
        return "users_detail";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("user", new User());
        return "users_add";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable long id) {
        model.addAttribute("user", userService.getUser(id));
        return "users_add";
    }

    @PostMapping("/save")
    public String addSave(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/users/";
    }

    @GetMapping("/{id}/delete")
    public String delete(Model model, @PathVariable long id) {
        model.addAttribute("user", userService.getUser(id));
        return "users_delete";
    }

    @PostMapping("/{id}/delete")
    public String deleteConfirm(@PathVariable long id) {
        userService.deleteUser(id);
        return "redirect:/users/";
    }
}
