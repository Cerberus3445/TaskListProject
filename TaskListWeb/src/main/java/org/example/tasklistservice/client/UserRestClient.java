package org.example.tasklistservice.client;

import lombok.RequiredArgsConstructor;
import org.example.tasklistservice.domain.user.User;
import org.example.tasklistservice.dto.PasswordDto;
import org.example.tasklistservice.dto.UserDto;
import org.example.tasklistservice.exception.BadRequestException;
import org.example.tasklistservice.exception.UserNotCreatedException;
import org.example.tasklistservice.exception.UserNotFoundException;
import org.example.tasklistservice.exception.UserNotUpdatedException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class UserRestClient {

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final RestTemplate template = new RestTemplate();

    public User getUser(int id){
        try {
            String url = "http://localhost:9002/api/user/" + id;
            UserDto user = template.exchange(url, HttpMethod.GET, returnHttpHeadersWithBasicAuthForGetRequest(), UserDto.class).getBody();
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
        User thisUser = getUser(id); //берём у того же юзера пароль(с модели приходит юзер без пароля, так как в бд он уже закодирован)
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(id));
        map.put("name", user.getName());
        map.put("email", user.getEmail());
        map.put("password", thisUser.getPassword());
        update(map, user.getId());
    }

    public void deleteUser(int id){
        try {
            String url = "http://localhost:9002/api/user/" + id + "/delete";
            template.exchange(url, HttpMethod.DELETE, returnHttpHeadersWithBasicAuthForGetRequest(), String.class);
        } catch (HttpClientErrorException.BadRequest badRequest){
            throw new BadRequestException();
        }
    }

    public void create(Map<String, String> hashmap){
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setBasicAuth("taskListService", "asfjsf82fdwsufhao12");
            HttpEntity<Object> request = new HttpEntity<>(hashmap, httpHeaders);
            String url = "http://localhost:9002/api/user";
            template.postForObject(url, request, UserDto.class);
        } catch (HttpClientErrorException.BadRequest badRequest){
            throw new UserNotCreatedException(badRequest.getMessage());
        }
    }

    public void update(Map<String, String> hashmap, int userId){
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setBasicAuth("taskListService", "asfjsf82fdwsufhao12");
            HttpEntity<Object> request = new HttpEntity<>(hashmap, httpHeaders);
            String url = "http://localhost:9002/api/user/%d/update".formatted(userId);
            template.postForObject(url, request, UserDto.class);
        } catch (HttpClientErrorException.BadRequest badRequest){
            throw new UserNotUpdatedException(badRequest.getMessage());
        }
    }

    public User findByEmail(String username) {
        try {
            String url = "http://localhost:9002/api/user/byEmail?email=" + username;
            UserDto user = template.exchange(url, HttpMethod.GET, returnHttpHeadersWithBasicAuthForGetRequest(), UserDto.class).getBody();
            return modelMapper.map(user, User.class);
        } catch (HttpClientErrorException.BadRequest badRequest){
            throw new UserNotUpdatedException(badRequest.getMessage());
        }
    }

    public void updatePassword(int id, String password){
        try {
            String url = "http://localhost:9002/api/user/%d/updatePassword".formatted(id);
            PasswordDto passwordDto = new PasswordDto(passwordEncoder.encode(password));
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth("taskListService", "asfjsf82fdwsufhao12");
            HttpEntity<Object> http = new HttpEntity<>(passwordDto, headers);
            template.postForObject(url,http, String.class);
        } catch (HttpClientErrorException.BadRequest badRequest){
            throw new UserNotUpdatedException(badRequest.getMessage());
        }
    }

    private HttpEntity<Objects> returnHttpHeadersWithBasicAuthForGetRequest(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("taskListService", "asfjsf82fdwsufhao12");
        HttpEntity<Objects> http = new HttpEntity<>(headers);
        return http;
    }
}
