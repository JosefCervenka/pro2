package cz.uhk.pro2_d;

import cz.uhk.pro2_d.model.User;
import cz.uhk.pro2_d.service.Interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Pro2DApplication {

    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Pro2DApplication(IUserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {
            addUser("User", "user", "heslo", "USER");
            addUser("Admin", "admin", "heslo", "ADMIN");
        };
    }

    private void addUser(String name, String username, String password, String role) {
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        userService.saveUser(user);
    }

    public static void main(String[] args) {
        SpringApplication.run(Pro2DApplication.class, args);
    }

}
