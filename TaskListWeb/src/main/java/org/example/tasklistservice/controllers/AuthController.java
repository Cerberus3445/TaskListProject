package org.example.tasklistservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.tasklistservice.client.UserRestClient;
import org.example.tasklistservice.domain.user.User;
import org.example.tasklistservice.exception.ErrorHandling;
import org.example.tasklistservice.exception.UserException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRestClient userRestClient;

    private final ErrorHandling errorHandling;


    @GetMapping("/registration")
    public String createUserPage(Model model){
        model.addAttribute("user", new User());
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String createUser(@ModelAttribute("user") User user, Model model){
        try {
            userRestClient.createUser(user);
            return "auth/login";
        } catch (UserException userException){
            model.addAttribute("errors", errorHandling.handleUserException(userException));
            return "auth/registration";
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }
}
