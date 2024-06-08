package org.example.tasklistservice.client;

import lombok.RequiredArgsConstructor;
import org.example.tasklistservice.domain.task.Status;
import org.example.tasklistservice.domain.task.Task;
import org.example.tasklistservice.dto.StatusDto;
import org.example.tasklistservice.dto.TaskDto;
import org.example.tasklistservice.exception.BadRequestException;
import org.example.tasklistservice.exception.TaskNotCreatedException;
import org.example.tasklistservice.exception.TaskNotUpdatedException;
import org.example.tasklistservice.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class TaskRestClient {

    private final RestTemplate restTemplate = new RestTemplate();

    private final UserRestClient userRestClient;

    private final ModelMapper modelMapper;

    public Task getTask(int userId, int taskId){
        try {
            String url = "http://localhost:9002/api/user/%d/tasks/%d".formatted(userId, taskId);
            TaskDto taskDto = restTemplate.exchange(url, HttpMethod.GET, returnHttpHeadersWithBasicAuthForGetRequest(), TaskDto.class).getBody();
            return modelMapper.map(taskDto, Task.class);
        } catch (HttpClientErrorException.NotFound notFound){
            throw new UserNotFoundException("User with this id not found");
        }
    }

    public List<Task> getDoneTasks(int userId){
        try {
            String url = "http://localhost:9002/api/user/%d/tasks/done".formatted(userId);
            TaskDto[] taskDtoArray = restTemplate.exchange(url, HttpMethod.GET, returnHttpHeadersWithBasicAuthForGetRequest(), TaskDto[].class).getBody();
            return fromTaskDtoArrayToTaskList(taskDtoArray, userId);
        } catch (HttpClientErrorException.BadRequest badRequest){
            throw new BadRequestException();
        }
    }

    public List<Task> getInProgressTasks(int userId){
        try {
            String url = "http://localhost:9002/api/user/%d/tasks/in_progress".formatted(userId);
            TaskDto[] taskDtoArray = restTemplate.exchange(url, HttpMethod.GET, returnHttpHeadersWithBasicAuthForGetRequest(), TaskDto[].class).getBody();
            return fromTaskDtoArrayToTaskList(taskDtoArray, userId);
        } catch (HttpClientErrorException.BadRequest badRequest){
            throw new BadRequestException();
        }
    }

    public List<Task> getPlannedTasks(int userId){
        try {
            String url = "http://localhost:9002/api/user/%d/tasks/planned".formatted(userId);
            TaskDto[] taskDtoArray = restTemplate.exchange(url, HttpMethod.GET, returnHttpHeadersWithBasicAuthForGetRequest(), TaskDto[].class).getBody();
            return fromTaskDtoArrayToTaskList(taskDtoArray, userId);
        } catch (HttpClientErrorException.BadRequest badRequest){
            throw new BadRequestException();
        }
    }

    public List<Task> getTasksByUserId(int id){
        try {
            String url = "http://localhost:9002/api/user/%d/tasks".formatted(id);
            List<Task> taskList = new ArrayList<>();
            TaskDto[] taskDtoArray = restTemplate.exchange(url, HttpMethod.GET, returnHttpHeadersWithBasicAuthForGetRequest(), TaskDto[].class).getBody();
            for (TaskDto dto : taskDtoArray) {
                taskList.add(modelMapper.map(dto, Task.class));
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
        map.put("expirationDate", taskDto.getExpirationDate().toString().replace("T", " "));
        map.put("status", taskDto.getStatus().toString());
        return map;
    }

    public List<Status> allStatus(){
        List<Status> statusList = new ArrayList<>();
        statusList.add(Status.PLANNED);
        statusList.add(Status.IN_PROGRESS);
        statusList.add(Status.DONE);
        return statusList;
    }

    public void createTask(int userId, TaskDto taskDto){
        try {
            taskDto.setStatus(Status.PLANNED);
            String url = "http://localhost:9002/api/user/%d/tasks/create".formatted(userId);
            Map<String, String> map = formatTaskDtoToJson(taskDto);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setBasicAuth("taskListService", "asfjsf82fdwsufhao12");
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
            taskDto.setStatus(Status.PLANNED);
            String url = "http://localhost:9002/api/user/%d/tasks/%d/update".formatted(userId, taskDto.getId());
            Map<String, String> map = formatTaskDtoToJson(taskDto);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setBasicAuth("taskListService", "asfjsf82fdwsufhao12");
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
            String url = "http://localhost:9002/api/user/%d/tasks/%d".formatted(userId, taskId);
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth("taskListService", "asfjsf82fdwsufhao12");
            HttpEntity<Objects> http = new HttpEntity<>(headers);
            restTemplate.exchange(url, HttpMethod.DELETE, http, String.class);
        } catch (HttpClientErrorException.BadRequest badRequest){
            throw new BadRequestException();
        }
    }

    public void setTaskStatus(int userId, int taskId, Status status){
        try {
            StatusDto statusDto = new StatusDto(status);
            String url = "http://localhost:9002/api/user/%d/tasks/%d/status".formatted(userId, taskId);
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth("taskListService", "asfjsf82fdwsufhao12");
            HttpEntity<Object> http = new HttpEntity<>(statusDto, headers);
            restTemplate.postForObject(url, http, String.class);
        } catch (Exception e){

        }
    }

    public LocalDateTime formatStringToLocalDataTime(String string){ //Форматируем expirationDate из JSON в localDateTime
        String[] array = string.split(" ");
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {
            if(i == 0){
                String yearMonthDay = array[i];
                String[] date = yearMonthDay.split("-");
                for(String s : date){
                    list.add(Integer.valueOf(s));
                }
            } else {
                String hourAndMinutes = array[i];
                String[] date = hourAndMinutes.split(":");
                for(String s : date){
                    list.add(Integer.valueOf(s));
                }
            }
        }

        return LocalDateTime.of(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4));
    }

    public String formatTaskStatusToString(Status status){
        switch (status){
            case DONE -> {
                return "СДЕЛАНО";
            } case PLANNED -> {
                return "ЗАПЛАНИРОВАННО";
            } case IN_PROGRESS -> {
                return "В ПРОЦЕССЕ";
            }default -> {
                return "ОШИБКА";
            }
        }
    }

    public String formatDateToString(LocalDateTime localDateTime){
        return localDateTime.toString().replace("T", " ");
    }

    private List<Task> fromTaskDtoArrayToTaskList(TaskDto[] taskDtoArray, int userId){
        List<Task> taskList = new ArrayList<>();
        for(TaskDto taskDto : taskDtoArray){
            Task task = modelMapper.map(taskDto, Task.class);
            task.setUser(userRestClient.getUser(userId));
            taskList.add(task);
        }
        return taskList;
    }

    private HttpEntity<Objects> returnHttpHeadersWithBasicAuthForGetRequest(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("taskListService", "asfjsf82fdwsufhao12");
        HttpEntity<Objects> http = new HttpEntity<>(headers);
        return http;
    }
}
