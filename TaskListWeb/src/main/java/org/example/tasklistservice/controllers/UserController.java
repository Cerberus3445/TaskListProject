package org.example.tasklistservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.tasklistservice.domain.user.User;
import org.example.tasklistservice.client.UserRestClient;
import org.example.tasklistservice.exception.UserNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web/user")
public class UserController {

    private final UserRestClient userRestClient;


    @GetMapping("/{id}")
    public String showUserById(@PathVariable("id") int id, Model model){
        try {
            model.addAttribute("user", userRestClient.getUser(id));
        } catch (UserNotFoundException userNotFoundException){
            return "errors/404";
        }
        return "users/aboutUser";
    }

    @GetMapping("/{id}/update")
    public String updateUserPage(@PathVariable("id") int id, Model model){
        model.addAttribute("user", userRestClient.getUser(id));
        return "users/update";
    }

    @PostMapping("/{id}/update")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") int id){
        userRestClient.update(user, id);
        return "redirect:/web/user/1";
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable("id") int id){
        userRestClient.deleteUser(id);
        return "/auth/login";
    }
}
