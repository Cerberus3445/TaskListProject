package org.example.tasklistservice.client;

import lombok.RequiredArgsConstructor;
import org.example.tasklistservice.domain.task.Status;
import org.example.tasklistservice.domain.task.Task;
import org.example.tasklistservice.dto.TaskDto;
import org.example.tasklistservice.exception.BadRequestException;
import org.example.tasklistservice.exception.TaskNotCreatedException;
import org.example.tasklistservice.exception.TaskNotUpdatedException;
import org.example.tasklistservice.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
            TaskDto taskDto = restTemplate.getForObject(url, TaskDto.class);
            return modelMapper.map(taskDto, Task.class);
        } catch (HttpClientErrorException.NotFound notFound){
            throw new UserNotFoundException("User with this id not found");
        }
    }

    public List<Task> getDoneTasks(int userId){
        try {
            String url = "http://localhost:9002/api/user/%d/tasks/done".formatted(userId);
            TaskDto[] taskDtoArray = restTemplate.getForObject(url, TaskDto[].class);
            return fromTaskDtoArrayToTaskList(taskDtoArray, userId);
        } catch (HttpClientErrorException.BadRequest badRequest){
            throw new BadRequestException();
        }
    }

    public List<Task> getInProgressTasks(int userId){
        try {
            String url = "http://localhost:9002/api/user/%d/tasks/in_progress".formatted(userId);
            TaskDto[] taskDtoArray = restTemplate.getForObject(url, TaskDto[].class);
            return fromTaskDtoArrayToTaskList(taskDtoArray, userId);
        } catch (HttpClientErrorException.BadRequest badRequest){
            throw new BadRequestException();
        }
    }

    public List<Task> getPlannedTasks(int userId){
        try {
            String url = "http://localhost:9002/api/user/%d/tasks/planned".formatted(userId);
            TaskDto[] taskDtoArray = restTemplate.getForObject(url, TaskDto[].class);
            return fromTaskDtoArrayToTaskList(taskDtoArray, userId);
        } catch (HttpClientErrorException.BadRequest badRequest){
            throw new BadRequestException();
        }
    }

    public List<Task> getTasksByUserId(int id){
        try {
            String url = "http://localhost:9002/api/user/%d/tasks".formatted(id);
            List<Task> taskList = new ArrayList<>();
            TaskDto[] taskDto = restTemplate.getForObject(url, TaskDto[].class);
            for (TaskDto dto : taskDto) {
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
            restTemplate.delete(url);
        } catch (HttpClientErrorException.BadRequest badRequest){
            throw new BadRequestException();
        }
    }

    public void setTaskStatus(int userId, int taskId, Status status){
        Map<String, String> map = new HashMap<>();
        try {
            map.put("status", status.toString());
            String url = "http://localhost:9002/api/user/%d/tasks/%d/status".formatted(userId, taskId);
            restTemplate.postForObject(url, map, String.class);
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

        LocalDateTime localDateTime = LocalDateTime.of(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4));
        return localDateTime;
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
}
