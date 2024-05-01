package org.example.tasklistservice.client;

import lombok.RequiredArgsConstructor;
import org.example.tasklistservice.domain.user.User;
import org.example.tasklistservice.dto.UserDto;
import org.example.tasklistservice.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class UserRestClient {

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final RestTemplate template = new RestTemplate();

    public User getUser(int id){
        try {
            String url = "http://localhost:9000/api/user/" + id;
            UserDto user = template.getForObject(url, UserDto.class);
            return modelMapper.map(user, User.class);
        } catch (HttpClientErrorException.NotFound notFound){
            throw new UserNotFoundException(notFound.getMessage());
        }
    }

    public void createUser(User user){
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(user.getId()));
        map.put("name", user.getName());
        map.put("email", user.getEmail());
        map.put("password", passwordEncoder.encode(user.getPassword()));
        create(map);
    }

    public void update(User user, int id){
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(id));
        map.put("name", user.getName());
        map.put("email", user.getEmail());
        map.put("password", passwordEncoder.encode(user.getPassword()));
        update(map, user.getId());
    }

    public void deleteUser(int id){
        try {
            String url = "http://localhost:9000/api/user/" + id + "/delete";
            template.delete(url);
        } catch (HttpClientErrorException.NotFound notFound){
            throw new UserNotFoundException(notFound.getMessage());
        }
    }

    public void create(Map<String, String> hashmap){
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> request = new HttpEntity<>(hashmap, httpHeaders);
            String url = "http://localhost:9000/api/user";
            UserDto string = template.postForObject(url, request, UserDto.class);
        } catch (Exception e){

        }
    }

    public void update(Map<String, String> hashmap, int userId){
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> request = new HttpEntity<>(hashmap, httpHeaders);
            String url = "http://localhost:9000/api/user/" + userId + "/update";
            UserDto string = template.postForObject(url, request, UserDto.class);
        } catch (Exception e){

        }
    }
}
