package org.example.tasklistservice.client;

import lombok.RequiredArgsConstructor;
import org.example.tasklistservice.domain.task.Status;
import org.example.tasklistservice.domain.task.Task;
import org.example.tasklistservice.dto.TaskDto;
import org.example.tasklistservice.exception.TaskNotCreatedException;
import org.example.tasklistservice.exception.TaskNotUpdatedException;
import org.example.tasklistservice.exception.UserNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, String> formatTaskDtoToJson(TaskDto taskDto){
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(taskDto.getId()));
        map.put("title", taskDto.getTitle());
        map.put("description", taskDto.getDescription());
        map.put("expirationDate", taskDto.getExpirationDate());
        map.put("status", taskDto.getStatus().toString());
        return map;
    }

    public void createTask(int userId, TaskDto taskDto){
        try {
            taskDto.setStatus(Status.PLANNED);
            String url = "http://localhost:9000/api/user/%d/tasks/create".formatted(userId);
            Map<String, String> map = formatTaskDtoToJson(taskDto);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> request = new HttpEntity<>(map, httpHeaders);
            restTemplate.postForObject(url, request, String.class);
        } catch (HttpClientErrorException.NotFound notFound){
            throw new UserNotFoundException("User with this id not found");
        } catch (HttpClientErrorException.BadRequest badRequest){
            throw new TaskNotCreatedException(badRequest.getMessage());
        }
    }

    public void updateTask(int userId, TaskDto taskDto){
        try {
            taskDto.setStatus(Status.PLANNED); //TODO
            String url = "http://localhost:9000/api/user/%d/tasks/%d/update".formatted(userId, taskDto.getId());
            Map<String, String> map = formatTaskDtoToJson(taskDto);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> request = new HttpEntity<>(map, httpHeaders);
            restTemplate.postForObject(url, request, String.class);
        } catch (HttpClientErrorException.NotFound notFound){
            throw new UserNotFoundException("User or task with this id not found");
        } catch (HttpClientErrorException.BadRequest badRequest){
            throw new TaskNotUpdatedException(badRequest.getMessage());
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

    public LocalDateTime formatStringToLocalDataTime(String string){ //Форматируем expirationDate из JSON в localDateTime
        String[] array = string.split("-");
        List<Integer> list = new ArrayList<>();
        for (String x : array) {
            list.add(Integer.valueOf(x));
        }
        LocalDateTime localDateTime = LocalDateTime.of(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4));
        return localDateTime;
    }

    public Task toTask(TaskDto taskDto, int userId){ //Маппим в таску вручную, так как ModelMapper не может десереализовать и кидается Jackson exception
        Task task = new Task();
        task.setUser(userRestClient.getUser(userId));
        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setStatus(taskDto.getStatus());
        task.setDescription(taskDto.getDescription());
        task.setExpirationDate(formatStringToLocalDataTime(taskDto.getExpirationDate()));
        return task;
    }

    public TaskDto toTaskDto(Task task){
        String time = convertLocalDataTimeFromTaskDtoToString(task.getExpirationDate().toString());
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setStatus(task.getStatus());
        taskDto.setExpirationDate(time);
        return taskDto;
    }

    public String convertLocalDataTimeFromTaskDtoToString(String expirationDate){
        expirationDate = expirationDate.replaceAll("-", " ");
        expirationDate = expirationDate.replaceAll(":", " ");
        expirationDate = expirationDate.replaceAll("T", " ");
        expirationDate = expirationDate.replaceAll(" ", "-");
        return expirationDate;
    }
}
