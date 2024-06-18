package rest.microservices.tasklistapiclone.services;


import rest.microservices.tasklistapiclone.domain.task.Task;
import rest.microservices.tasklistapiclone.domain.user.User;
import rest.microservices.tasklistapiclone.dto.PasswordDto;

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
