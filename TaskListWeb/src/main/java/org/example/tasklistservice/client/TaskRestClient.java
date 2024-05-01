package org.example.tasklistservice.client;

import lombok.RequiredArgsConstructor;
import org.example.tasklistservice.domain.task.Task;
import org.example.tasklistservice.dto.TaskDto;
import org.example.tasklistservice.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskRestClient {

    private final RestTemplate restTemplate = new RestTemplate();

    private final UserRestClient userRestClient;

    public Task getTask(int userId, int taskId){
        try {
            String url = "http://localhost:9000/api/user/%d/tasks/%d".formatted(userId, taskId);
            TaskDto taskDto = restTemplate.getForObject(url, TaskDto.class);
            return toTask(taskDto, userId);
        } catch (HttpClientErrorException.NotFound notFound){
            throw new UserNotFoundException("User with this id not found");
        }
    }

    public List<Task> getTasksByUserId(int id){
        try {
            String url = "http://localhost:9000/api/user/%d/tasks".formatted(id);
            List<Task> taskList = new ArrayList<>();
            TaskDto[] taskDto = restTemplate.getForObject(url, TaskDto[].class);
            for(int i = 0; i < taskDto.length; i++){
                taskList.add(toTask(taskDto[i], id));
            }
            return taskList;
        } catch (HttpClientErrorException.NotFound notFound){
            throw new UserNotFoundException("User with this id not found");
        }
    }

    public void deleteTask(int userId, int taskId){
        try {
            String url = "http://localhost:9000/api/user/%d/tasks/%d".formatted(userId, taskId);
            restTemplate.delete(url);
        } catch (HttpClientErrorException.NotFound notFound){
            throw new UserNotFoundException("Task with this id not found");
        }
    }

    public LocalDateTime formatStringToLocalDataTime(String string){
        String[] array = string.split("-");
        List<Integer> list = new ArrayList<>();
        for (String x : array) {
            list.add(Integer.valueOf(x));
        }
        LocalDateTime localDateTime = LocalDateTime.of(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4));
        return localDateTime;
    }

    public Task toTask(TaskDto taskDto, int userId){
        Task task = new Task();
        task.setUser(userRestClient.getUser(userId));
        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setStatus(taskDto.getStatus());
        task.setDescription(taskDto.getDescription());
        task.setExpirationDate(formatStringToLocalDataTime(taskDto.getExpirationDate()));
        return task;
    }
}
