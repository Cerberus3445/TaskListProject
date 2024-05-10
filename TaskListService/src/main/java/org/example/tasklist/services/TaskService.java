package org.example.tasklist.services;

import lombok.RequiredArgsConstructor;
import org.example.tasklist.domain.exception.TaskNotFoundException;
import org.example.tasklist.domain.task.Status;
import org.example.tasklist.domain.task.Task;
import org.example.tasklist.repositories.TaskRepository;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

    private final UserService userService;

    private final TaskRepository taskRepository;


    public Task createTask(int userId, Task task){
        task.setUser(userService.showUserById(userId));
        Task createdTask = taskRepository.save(task);
        return createdTask;
    }

    public Task getTaskById(int id){
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    public Task updateTask(int taskId, Task updatedTask){
        Task task = getTaskById(taskId);
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        task.setExpirationDate(updatedTask.getExpirationDate());
        taskRepository.save(task);
        return task;
    }

    public void changeStatus(int taskId, Status status){
        Task task = getTaskById(taskId);
        task.setStatus(status);
    }

    public void deleteTask(int id){
        taskRepository.deleteById(id);
    }
}
