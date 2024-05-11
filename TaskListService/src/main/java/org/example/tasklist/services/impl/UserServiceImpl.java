package org.example.tasklist.services.impl;

import lombok.RequiredArgsConstructor;

import org.example.tasklist.domain.MailType;
import org.example.tasklist.domain.exception.UserNotFoundException;
import org.example.tasklist.domain.task.Task;
import org.example.tasklist.domain.user.User;
import org.example.tasklist.repositories.UserRepository;
import org.example.tasklist.services.MailService;
import org.example.tasklist.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Properties;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final MailService mailService;


    @Override
    public User showUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public User createUser(User user){
        User user1 = userRepository.save(user);
        mailService.sendEmail(user, MailType.REGISTRATION, new Properties());
        return user1;
    }

    @Override
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public User updateUser(int id, User userUpdated){
        userUpdated.setId(id);
        userRepository.save(userUpdated);
        return userUpdated;
    }

    @Override
    public List<Task> getUserTasks(User user){
        return user.getTasks();
    }

    @Override
    public void deleteUserById(int id){
        userRepository.deleteById(id);
    }

}
