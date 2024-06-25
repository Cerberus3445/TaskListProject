package org.example.tasklistservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.tasklistservice.domain.user.User;
import org.example.tasklistservice.client.UserRestClient;
import org.example.tasklistservice.exception.ErrorHandling;
import org.example.tasklistservice.exception.UserException;
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

    private final ErrorHandling errorHandling;

    @GetMapping("/aboutUser")
    public String getUser(Model model){
        try {
            User user = userRestClient.getUser(getUserId());
            model.addAttribute("user", user);
            if(user.getRole().equals("ROLE_ADMIN")){
                model.addAttribute("admin", "");
            }
        } catch (Exception e){
            return "errors/404";
        }
        return "users/aboutUser";
    }

    @GetMapping("/aboutUser/update")
    public String updateUserPage(Model model){
        model.addAttribute("user", userRestClient.getUser(getUserId()));
        return "users/update";
    }

    @GetMapping("/aboutUser/updatePassword")
    public String updatePasswordPage(){
        return "users/updatePassword";
    }

    @PostMapping("/aboutUser/updatePassword")
    public String updatePassword(@ModelAttribute("password") String password, Model model){
        System.out.println(password);
        try {
            userRestClient.updatePassword(getUserId(), password);
        } catch (UserException userException){
            model.addAttribute("error",  errorHandling.handleUserAndTaskException(userException));
            return "users/updatePassword";
        }
        return "redirect:/web/user/aboutUser";
    }

    @PostMapping("/{id}/update")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") int id, Model model){
        try {
            userRestClient.updateUser(user, id);
        } catch (UserException userException){
            model.addAttribute("error",  errorHandling.handleUserAndTaskException(userException));
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
