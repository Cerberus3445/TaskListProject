package org.example.tasklistservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class HelloPageController {

    @GetMapping
    public String helloPage(){
        return "hello-page";
    }

    @GetMapping("/toAuth")
    public String toAuthPage(){
        return "auth/login";
    }

    @GetMapping("/toRegister")
    public String toRegistrationPage(){
        return "auth/registration";
    }
}
