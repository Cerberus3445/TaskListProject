package org.example.tasklist.services;

import lombok.RequiredArgsConstructor;

import org.example.tasklist.domain.exception.UserNotFoundException;
import org.example.tasklist.domain.task.Task;
import org.example.tasklist.domain.user.User;
import org.example.tasklist.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public User showUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User createUser(User user){
        User user1 = userRepository.save(user);
        return user1;
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
