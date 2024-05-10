package org.example.tasklistservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.tasklistservice.domain.user.User;
import org.example.tasklistservice.client.UserRestClient;
import org.example.tasklistservice.exception.UserNotFoundException;
import org.example.tasklistservice.exception.UserNotUpdatedException;
import org.example.tasklistservice.security.PersonDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web/user")
public class UserController {

    private final UserRestClient userRestClient;

    @GetMapping("/aboutUser")
    public String showUserById(Model model){
        try {
            model.addAttribute("user", userRestClient.getUser(getUserId()));
        } catch (UserNotFoundException userNotFoundException){
            return "errors/404";
        }
        return "users/aboutUser";
    }

    @GetMapping("/aboutUser/update")
    public String updateUserPage(Model model){
        model.addAttribute("user", userRestClient.getUser(getUserId()));
        return "users/update";
    }

    @PostMapping("/{id}/update")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") int id, Model model){
        try {
            userRestClient.update(user, id);
        } catch (UserNotUpdatedException userNotUpdatedException){
            model.addAttribute("error",  userNotUpdatedException.getMessage());
            return "users/update";
        }
        return "redirect:/web/user/aboutUser";
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable("id") int id){
        userRestClient.deleteUser(id);
        return "/auth/login";
    }

    public int getUserId(){
         PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         return personDetails.getUserId();
    }
}
