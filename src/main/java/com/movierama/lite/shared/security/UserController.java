package com.movierama.lite.shared.security;

import com.movierama.lite.shared.dto.MovieramaUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {


    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public String signup(@RequestParam("username") String username, @RequestParam("password") String password) {
        try {
            userService.saveUser(new User(username, passwordEncoder.encode(password)));
        } catch (Exception e) {
            return "signup";
        }
        return "redirect:/login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/login")
    public String login(@AuthenticationPrincipal MovieramaUser user) {
        return "login";
    }
}
