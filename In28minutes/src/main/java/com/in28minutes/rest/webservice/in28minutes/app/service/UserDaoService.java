package com.in28minutes.rest.webservice.in28minutes.app.service;

import com.in28minutes.rest.webservice.in28minutes.app.entity.User;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.net.http.HttpHeaders;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<>();

    private static Integer count = 0;

    static {
        users.add(new User(++count, "Adam", LocalDate.now().minusYears(30)));
        users.add(new User(++count, "Eva", LocalDate.now().minusYears(25)));
        users.add(new User(++count, "Jim", LocalDate.now().minusYears(20)));
    }

    public List<User> findAll(){
        return users;
    }

    public User getUser(int id){
        return users.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    public User createUser(User user){
        user.setId(++count);
        users.add(user);
        return user;
    }

    public void delete(int id){
        User user = getUser(id);
        users.remove(user);
    }
}
