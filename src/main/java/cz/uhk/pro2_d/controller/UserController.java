package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.model.User;
import cz.uhk.pro2_d.service.Interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users_list";
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable long id) {
        model.addAttribute("user", userService.getUser(id));
        return "users_detail";
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("user", new User());
        return "users_add";
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable long id) {
        model.addAttribute("user", userService.getUser(id));
        return "users_add";
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public String addSave(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/users/";
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/delete")
    public String delete(Model model, @PathVariable long id) {
        model.addAttribute("user", userService.getUser(id));
        return "users_delete";
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/delete")
    public String deleteConfirm(@PathVariable long id) {
        userService.deleteUser(id);
        return "redirect:/users/";
    }
}
