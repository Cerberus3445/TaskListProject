package org.example.tasklistservice.client;

import lombok.RequiredArgsConstructor;
import org.example.tasklistservice.domain.user.User;
import org.example.tasklistservice.dto.UserDto;
import org.example.tasklistservice.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class RestClient{

    private final ModelMapper modelMapper;

    public User getUser(int id){
        try {
            RestTemplate template = new RestTemplate();
            String url = "http://localhost:9000/api/user/" + id;
            UserDto user =  template.getForObject(url, UserDto.class);
            return modelMapper.map(user, User.class);
        } catch (HttpClientErrorException.NotFound notFound){
            throw new UserNotFoundException(notFound.getMessage());
        }
    }

}
