package cz.uhk.pro2_d.rest;

import cz.uhk.pro2_d.model.User;
import cz.uhk.pro2_d.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/rest/users/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/rest/users/get/{id}")
    public User getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @PostMapping("/rest/users/new")
    public String newUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return user.toString();
    }

    @PutMapping("/rest/users/update/{id}")
    public String updateUser(@ModelAttribute User user) {
        //co když user neexistuje?
        userService.saveUser(user);
        return user.toString();
    }

    @DeleteMapping("/rest/users/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return "ok";
    }

}
