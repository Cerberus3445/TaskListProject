package org.example.tasklist.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.example.tasklist.domain.user.User;
import org.example.tasklist.dto.UserDto;
import org.example.tasklist.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
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
    public UserDto createUser(@RequestBody UserDto userDto){
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
