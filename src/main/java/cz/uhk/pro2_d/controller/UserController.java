package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.model.User;
import cz.uhk.pro2_d.service.Interfaces.ICommentService;
import cz.uhk.pro2_d.service.Interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private IUserService userService;
    private ICommentService commentService;

    @Autowired
    public UserController(IUserService userService, ICommentService commentService) {
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user/index";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable long id) {
        model.addAttribute("user", userService.getUser(id));
        return "user/detail";
    }

    @GetMapping("/create")
    public String add(Model model) {
        model.addAttribute("user", new User());
        return "user/create";
    }

    @GetMapping("/update/{id}")
    public String edit(Model model, @PathVariable long id) {
        model.addAttribute("user", userService.getUser(id));
        return "user/create";
    }

    @PostMapping("/create")
    public String addSave(@ModelAttribute User user) {

        var oldUser = userService.getUser(user.getId());
        user.setPassword(oldUser.getPassword());

        userService.saveUser(user);
        return "redirect:/user/" + user.getId();
    }

    @PostMapping("/delete/{id}")
    public String deleteConfirm(@PathVariable long id) {

        var user = userService.getUser(id);

        var comments = user.getComments();

        for (var comment : comments) {
            commentService.delete(comment.getId());
        }

        userService.deleteUser(user.getId());

        return "redirect:/user";
    }
}
