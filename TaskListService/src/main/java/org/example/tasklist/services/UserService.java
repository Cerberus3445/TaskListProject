package org.example.tasklist.services;

import org.example.tasklist.domain.task.Task;
import org.example.tasklist.domain.user.User;
import org.example.tasklist.dto.PasswordDto;

import java.util.List;

public interface UserService {

    User getUser(int id);

    User createUser(User user);

    User findByEmail(String email);

    User updateUser(int id, User userUpdated);

    List<Task> getUserTasks(User user);

    void deleteUserById(int id);

    void updatePassword(int id, PasswordDto password);
}
