package org.example.tasklist.services.impl;

import lombok.RequiredArgsConstructor;

import org.example.tasklist.domain.MailType;
import org.example.tasklist.domain.exception.UserNotFoundException;
import org.example.tasklist.domain.task.Task;
import org.example.tasklist.domain.user.User;
import org.example.tasklist.dto.PasswordDto;
import org.example.tasklist.repositories.UserRepository;
import org.example.tasklist.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User getUser(int id){
        logger.info("Get user with %d".formatted(id));
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public User createUser(User user){
        logger.info("Create user: " + user.toString());
        user.setRole("ROLE_USER");
        User user1 = userRepository.save(user);
        mailServiceImpl.sendEmail(user, MailType.REGISTRATION, new Properties());
        return user1;
    }

    @Override
    public User findByEmail(String email){
        logger.info("Find user by email %s".formatted(email));
        return userRepository.findByEmail(email);
    }

    @Override
    public User updateUser(int id, User userUpdated){
        logger.info("Update user with %d id ".formatted(id) + userUpdated.toString());
        userUpdated.setId(id);
        userRepository.save(userUpdated);
        return userUpdated;
    }

    @Override
    public List<Task> getUserTasks(User user){
        logger.info("Get user tasks");
        return user.getTasks();
    }

    @Override
    public void deleteUserById(int id){
        logger.info("Delete user with %d id".formatted(id));
        userRepository.deleteById(id);
    }

    @Override
    public void updatePassword(int id, PasswordDto password) {
        logger.info("Update password");
        Optional<User> user = userRepository.findById(id);
        user.get().setPassword(password.getPassword());
    }

}
