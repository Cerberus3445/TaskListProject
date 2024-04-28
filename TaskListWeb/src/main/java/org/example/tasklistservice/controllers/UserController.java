package org.example.tasklistservice.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.tasklistservice.domain.user.User;
import org.example.tasklistservice.client.RestClient;
import org.example.tasklistservice.exception.UserNotFoundException;
import org.example.tasklistservice.util.ExceptionBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web")
public class UserController {

    private final RestClient restClient;


    @GetMapping("/{id}")
    public String showUserById(@PathVariable("id") int id, Model model){
        try {
            model.addAttribute("user", restClient.getUser(id));
        } catch (UserNotFoundException userNotFoundException){
            return "errors/404";
        }
        return "users/aboutUser";
    }

    @GetMapping("/hello")
    public String hello(){
        return "users/hello";
    }

    @GetMapping("/create")
    public String createUserPage(Model model){
        model.addAttribute("user", new User());
        return "users/create";
    }

}
