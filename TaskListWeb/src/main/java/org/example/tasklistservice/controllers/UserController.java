package org.example.tasklistservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.tasklistservice.domain.user.User;
import org.example.tasklistservice.client.UserRestClient;
import org.example.tasklistservice.exception.UserNotFoundException;
import org.example.tasklistservice.security.PersonDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "users/update";
        }
        userRestClient.update(user, id);
        return "redirect:/web/user/1";
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
