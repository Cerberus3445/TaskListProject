package com.in28minutes.rest.webservice.in28minutes.app.controller;

import com.in28minutes.rest.webservice.in28minutes.app.entity.Post;
import com.in28minutes.rest.webservice.in28minutes.app.entity.User;
import com.in28minutes.rest.webservice.in28minutes.app.exception.PostNotFoundException;
import com.in28minutes.rest.webservice.in28minutes.app.exception.UserNotFoundException;
import com.in28minutes.rest.webservice.in28minutes.app.repository.PostRepository;
import com.in28minutes.rest.webservice.in28minutes.app.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@RestController
@RequestMapping("/jpa")
public class UserJpaResource {

    private final MessageSource messageSource;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    @Autowired
    public UserJpaResource(MessageSource messageSource, UserRepository userRepository, PostRepository postRepository) {
        this.messageSource = messageSource;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrievePostsForUsers(@PathVariable("id") int id){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with this id not found"));

        return user.getPostList();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable("id") int id){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with this id not found"));

        EntityModel<User> entityModel = EntityModel.of(user);

        WebMvcLinkBuilder webMvcLinkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        //В этом классе берём ссылку метода retrieveAllUsers
        entityModel.add(webMvcLinkBuilder.withRel("all_users"));

        return entityModel;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") int id){
       userRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<Objects> createUser(@Valid @RequestBody User user){
        User savedUser = userRepository.save(user);
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

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Objects> createPost(@PathVariable("id") int id, @Valid @RequestBody Post post ){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with this id not found"));
        post.setUser(user);
        Post savedPost = postRepository.save(post);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{postId}").buildAndExpand(savedPost.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/users/{id}/posts/{postId}")
    public Post getPost(@PathVariable("postId") int postId ){
       return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post with id not found"));
    }
}
