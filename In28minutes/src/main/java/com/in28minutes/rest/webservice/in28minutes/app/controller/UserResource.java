package com.in28minutes.rest.webservice.in28minutes.app.controller;

import com.in28minutes.rest.webservice.in28minutes.app.entity.User;
import com.in28minutes.rest.webservice.in28minutes.app.exception.UserNotFoundException;
import com.in28minutes.rest.webservice.in28minutes.app.service.UserDaoService;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@RestController
public class UserResource {

    private final MessageSource messageSource;

    private final UserDaoService userDaoService;

    public UserResource(MessageSource messageSource, UserDaoService userDaoService) {
        this.messageSource = messageSource;
        this.userDaoService = userDaoService;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userDaoService.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable("id") int id){

        User user = userDaoService.getUser(id);

        if(user == null){
            throw new UserNotFoundException("User with %d not found".formatted(id));
        }

        EntityModel<User> entityModel = EntityModel.of(user);

        WebMvcLinkBuilder webMvcLinkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        //В этом классе берём ссылку метода retrieveAllUsers
        entityModel.add(webMvcLinkBuilder.withRel("all_users"));

        return entityModel;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") int id){
       userDaoService.delete(id);
    }

    @PostMapping("/users")
    public ResponseEntity<Objects> createUser(@Valid @RequestBody User user){
        User savedUser = userDaoService.createUser(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest() //с текущего запроса /users
                .path("/{id}") // добавить это
                .buildAndExpand(savedUser.getId()) //вставить значение id
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/hello")
    public String helloWorldInternationalized(){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message", null , locale);
    }
}
