package rest.microservices.tasklistapiclone.services;


import rest.microservices.tasklistapiclone.domain.task.Status;
import rest.microservices.tasklistapiclone.domain.task.Task;

import java.util.List;

public interface TaskService {

    Task createTask(int userId, Task task);

    Task getTaskById(int id);

    Task updateTask(int taskId, Task updatedTask);

    void changeStatus(int taskId, Status status);

    void deleteTask(int id);

    List<Task> getTasksByStatus(Status status, int userId);
}
