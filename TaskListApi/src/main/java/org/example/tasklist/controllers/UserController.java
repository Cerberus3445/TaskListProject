package org.example.tasklist.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.tasklist.domain.exception.UserException;
import org.example.tasklist.domain.user.User;
import org.example.tasklist.dto.PasswordDto;
import org.example.tasklist.dto.UserDto;
import org.example.tasklist.services.impl.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/tasklist-api/user")
@Tag(name = "User Controller", description = "User API")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    private final ModelMapper modelMapper;

    private final Environment environment;

    @PostMapping
    @Operation(summary = "Create user")
    public UserDto createUser(@RequestBody @Valid UserDto userDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errors = new ArrayList<>();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(fieldError.getField()).append(" - ").append(fieldError.getDefaultMessage());
                errors.add(stringBuilder.toString());
            }
            throw new UserException(errors.toString());
        }
        User user = modelMapper.map(userDto, User.class);
        userServiceImpl.createUser(user);
        userDto.setEnvironment(environment.getProperty("local.server.port"));
        return userDto;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Show user by their id")
    public UserDto showUserById(@PathVariable("id") int id){
        User user = userServiceImpl.getUser(id);
        UserDto userDto =  modelMapper.map(user, UserDto.class);
        userDto.setEnvironment(environment.getProperty("local.server.port"));
        return userDto;
    }

    @GetMapping("/byEmail")
    @Operation(summary = "Get user by email(for Spring Security)")
    public UserDto getUserByEmail(@RequestParam("email") String email){
        User user = userServiceImpl.findByEmail(email);
        if(user == null) return null;

        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setEnvironment(environment.getProperty("local.server.port"));
        return userDto;
    }

    @PostMapping("/{id}/update")
    @Operation(summary = "Update user")
    public UserDto updateUser(@PathVariable("id") int id, @RequestBody @Valid UserDto userDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errors = new ArrayList<>();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(fieldError.getField()).append(" - ").append(fieldError.getDefaultMessage());
                errors.add(stringBuilder.toString());
            }
            throw new UserException(errors.toString());
        }
        User user = modelMapper.map(userDto, User.class);
        userServiceImpl.updateUser(id, user);
        userDto.setEnvironment(environment.getProperty("local.server.port"));
        return userDto;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user")
    public HttpStatus deleteUser(@PathVariable("id") int id){
        userServiceImpl.deleteUserById(id);
        return HttpStatus.OK;
    }

    @PostMapping("/{id}/updatePassword")
    @Operation(summary = "Update user password")
    public HttpStatus updateUserPassword(@RequestBody @Valid PasswordDto passwordDto, BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            throw new UserException("Длина пароля должен составлять от 5 до 120 символов");
        }
        userServiceImpl.updatePassword(id, passwordDto);
        return HttpStatus.OK;
    }

}
