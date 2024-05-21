package org.example.tasklist.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.tasklist.domain.exception.TaskNotFoundException;
import org.example.tasklist.domain.task.Status;
import org.example.tasklist.domain.task.Task;
import org.example.tasklist.repositories.TaskRepository;
import org.example.tasklist.services.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final UserServiceImpl userServiceImpl;

    private final TaskRepository taskRepository;


    @Override
    public Task createTask(int userId, Task task){
        task.setUser(userServiceImpl.showUserById(userId));
        Task createdTask = taskRepository.save(task);
        return createdTask;
    }

    @Override
    public Task getTaskById(int id){
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    @Override
    public Task updateTask(int taskId, Task updatedTask){
        Task task = getTaskById(taskId);
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        task.setExpirationDate(updatedTask.getExpirationDate());
        taskRepository.save(task);
        return task;
    }

    @Override
    public void changeStatus(int taskId, Status status){
        Task task = getTaskById(taskId);
        task.setStatus(status);
    }

    @Override
    public void deleteTask(int id){
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getTasksByStatus(Status status, int userId) {
        List<Task> taskList = userServiceImpl.getUserTasks(userServiceImpl.showUserById(userId));
        List<Task> requiredTasks = new ArrayList<>();
        for(Task task : taskList){
            if(task.getStatus() == status){
                requiredTasks.add(task);
            }
        }
        return requiredTasks;
    }
}
