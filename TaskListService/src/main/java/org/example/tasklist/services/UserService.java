package org.example.tasklist.services;

import org.example.tasklist.domain.task.Task;
import org.example.tasklist.domain.user.User;

import java.util.List;

public interface UserService {

    User showUserById(int id);

    User createUser(User user);

    User findByEmail(String email);

    User updateUser(int id, User userUpdated);

    List<Task> getUserTasks(User user);

    void deleteUserById(int id);

    void updatePassword(int id, String password);
}
