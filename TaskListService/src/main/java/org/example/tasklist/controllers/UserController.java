package org.example.tasklist.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.example.tasklist.domain.exception.TaskNotUpdatedException;
import org.example.tasklist.domain.exception.UserNotCreatedException;
import org.example.tasklist.domain.user.User;
import org.example.tasklist.dto.UserDto;
import org.example.tasklist.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "User Controller", description = "User API")
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    @PostMapping
    @Operation(summary = "Create user")
    public UserDto createUser(@RequestBody @Valid UserDto userDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder stringBuilder = new StringBuilder();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                stringBuilder.append(fieldError.getField()).append(" - ").append(fieldError.getDefaultMessage()).append(";");
            }
            throw new UserNotCreatedException(stringBuilder.toString());
        }
        User user = modelMapper.map(userDto, User.class);
        userService.createUser(user);
        return userDto;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Show user by their id")
    public UserDto showUserById(@PathVariable("id") int id){
        User user = userService.showUserById(id);
        return modelMapper.map(user, UserDto.class);
    }

    @PostMapping("/{id}/update")
    @Operation(summary = "Update user")
    public UserDto updateUser(@PathVariable("id") int id, @RequestBody UserDto userDto){
        User user = modelMapper.map(userDto, User.class);
        userService.updateUser(id, user);
        return userDto;
    }

    @DeleteMapping("/{id}/delete")
    @Operation(summary = "Delete user")
    public HttpStatus deleteUser(@PathVariable("id") int id){
        userService.deleteUserById(id);
        return HttpStatus.OK;
    }

}
