package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.model.User;
import cz.uhk.pro2_d.security.MyUserDetails;
import cz.uhk.pro2_d.service.Interfaces.IUserService;
import cz.uhk.pro2_d.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public IndexController(IUserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/register")
    public String register(String name, String username, String password, String repassword, Model model)
    {
        if(username.length() < 5)
        {
            model.addAttribute("error", "Username must be at least 5 characters");
        }

        if(name.length() < 5)
        {
            model.addAttribute("error", "Name must be at least 5 characters");
            return "register";
        }

        if(password.length() < 8)
        {
            model.addAttribute("error", "Password must be at least 8 characters");
            return "register";
        }

        if(!repassword.equals(password)) {
            model.addAttribute("error", "Passwords do not match");
            return "register";
        }
        Boolean userExists = userService.userExists(name);

        if(userExists) {
            model.addAttribute("error", "Username is already in use");
            return "register";
        }

        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        userService.saveUser(user);

        return "redirect:/login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/403")
    public String forbidden() {
        return "403";
    }

}
