package org.example.tasklist.services;

import lombok.RequiredArgsConstructor;

import org.example.tasklist.domain.MailType;
import org.example.tasklist.domain.exception.UserNotFoundException;
import org.example.tasklist.domain.task.Task;
import org.example.tasklist.domain.user.User;
import org.example.tasklist.repositories.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final MailService mailService;


    public User showUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User createUser(User user){
        User user1 = userRepository.save(user);
        mailService.sendEmail(user, MailType.REGISTRATION, new Properties());
        return user1;
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User updateUser(int id, User userUpdated){
        userUpdated.setId(id);
        userRepository.save(userUpdated);
        return userUpdated;
    }

    public List<Task> getUserTasks(User user){
        return user.getTasks();
    }

    public void deleteUserById(int id){
        userRepository.deleteById(id);
    }

}
