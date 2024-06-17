package org.example.tasklist.services.impl;

import lombok.RequiredArgsConstructor;

import org.example.tasklist.domain.MailType;
import org.example.tasklist.domain.exception.UserNotFoundException;
import org.example.tasklist.domain.task.Task;
import org.example.tasklist.domain.user.User;
import org.example.tasklist.dto.PasswordDto;
import org.example.tasklist.repositories.UserRepository;
import org.example.tasklist.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final MailServiceImpl mailServiceImpl;


    @Override
    public User getUser(int id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public User createUser(User user){
        user.setRole("ROLE_USER");
        User user1 = userRepository.save(user);
        mailServiceImpl.sendEmail(user, MailType.REGISTRATION, new Properties());
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

    @Override
    public void updatePassword(int id, PasswordDto password) {
        Optional<User> user = userRepository.findById(id);
        user.get().setPassword(password.getPassword());
    }

}
