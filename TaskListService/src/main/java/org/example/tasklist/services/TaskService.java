package org.example.tasklist.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.tasklist.domain.exception.TaskNotFoundException;
import org.example.tasklist.domain.task.Status;
import org.example.tasklist.domain.task.Task;
import org.example.tasklist.repositories.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public Task updateTask(Task updatedTask){
       Task task = taskRepository.save(updatedTask);
       return task;
    }

    public Task changeStatus(int taskId, Status status){
        Task task = getTaskById(taskId);
        task.setStatus(status);
        return task;
    }

    public void deleteTask(int id){
        taskRepository.deleteById(id);
    }

    public LocalDateTime formatStringToLocalDataTime(String string){
        String[] array = string.split("-");
        List<Integer> list = new ArrayList<>();
        for (String x : array){
            list.add(Integer.valueOf(x));
        }
        LocalDateTime localDateTime = LocalDateTime.of(list.get(0),list.get(1),list.get(2), list.get(3), list.get(4));
        return localDateTime;
    }

    public String convertLocalDataTimeFromTaskDtoToString(String expirationDate){
        expirationDate = expirationDate.replaceAll("-", " ");
        expirationDate = expirationDate.replaceAll(":", " ");
        expirationDate = expirationDate.replaceAll("T", " ");
        expirationDate = expirationDate.replaceAll(" ", "-");
        return expirationDate;
    }
}
