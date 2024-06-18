package rest.microservices.tasklistapiclone.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import rest.microservices.tasklistapiclone.domain.exception.TaskException;
import rest.microservices.tasklistapiclone.domain.task.Status;
import rest.microservices.tasklistapiclone.domain.task.Task;
import rest.microservices.tasklistapiclone.dto.TaskDto;
import rest.microservices.tasklistapiclone.services.impl.TaskServiceImpl;
import rest.microservices.tasklistapiclone.services.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/tasklist-api/user/{id}/tasks")
public class TaskController {

    private final UserServiceImpl userServiceImpl;

    private final TaskServiceImpl taskServiceImpl;

    private final ModelMapper modelMapper;

    private final Environment environment;

    @GetMapping
    public List<TaskDto> getTasksByUserId(@PathVariable("id") int id){
        List<Task> taskList = userServiceImpl.getUserTasks(userServiceImpl.getUser(id));
        List<TaskDto> taskDtoList = new ArrayList<>();
        for(Task task : taskList){
            TaskDto taskDto = modelMapper.map(task, TaskDto.class);
            taskDto.setEnvironment(environment.getProperty("local.server.port"));
            taskDtoList.add(taskDto);
        }
        return taskDtoList;
    }

    @GetMapping("/{taskId}")
    public TaskDto getTaskById(@PathVariable("taskId") int taskId){
        Task task = taskServiceImpl.getTaskById(taskId);
        TaskDto taskDto = modelMapper.map(task, TaskDto.class);
        taskDto.setEnvironment(environment.getProperty("local.server.port"));
        return taskDto;
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTaskDto(@PathVariable("id") int id, @RequestBody @Valid TaskDto taskDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errors = new ArrayList<>();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(fieldError.getField()).append(" - ").append(fieldError.getDefaultMessage());
                errors.add(stringBuilder.toString());
            }
            throw new TaskException(errors.toString());
        }
        Task task = modelMapper.map(taskDto, Task.class);
        taskServiceImpl.createTask(id, task);
        taskDto.setEnvironment(environment.getProperty("local.server.port"));
        return ResponseEntity.ok(taskDto);
    }

    @PostMapping("/{taskId}/update")
    public ResponseEntity<TaskDto> updateTask(@PathVariable("taskId") int id, @RequestBody @Valid TaskDto taskDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errors = new ArrayList<>();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(fieldError.getField()).append(" - ").append(fieldError.getDefaultMessage());
                errors.add(stringBuilder.toString());
            }
            throw new TaskException(errors.toString());
        }
        Task task = modelMapper.map(taskDto, Task.class);
        taskServiceImpl.updateTask(id, task);
        taskDto.setEnvironment(environment.getProperty("local.server.port"));
        return ResponseEntity.ok(taskDto);
    }

    @PostMapping("/{taskId}/status")
    public HttpStatus changeTaskStatus(@PathVariable("taskId") int taskId, @RequestParam("status") String status){
        taskServiceImpl.changeStatus(taskId, Status.valueOf(status));
        return HttpStatus.OK;
    }

    @DeleteMapping("/{taskId}")
    public HttpStatus deleteTaskById(@PathVariable("taskId") int taskId){
        taskServiceImpl.deleteTask(taskId);
        return HttpStatus.OK;
    }

    @GetMapping("/planned")
    public List<TaskDto> getPlannedTask(@PathVariable("id") int userId){
        List<TaskDto> taskDtoList = new ArrayList<>();
        for(Task task : taskServiceImpl.getTasksByStatus(Status.PLANNED, userId)){
            TaskDto taskDto = modelMapper.map(task, TaskDto.class);
            taskDto.setEnvironment(environment.getProperty("local.server.port"));
            taskDtoList.add(taskDto);
        }
        return taskDtoList;
    }

    @GetMapping("/in_progress")
    public List<TaskDto> getInProgressTask(@PathVariable("id") int userId){
        List<TaskDto> taskDtoList = new ArrayList<>();
        for(Task task : taskServiceImpl.getTasksByStatus(Status.IN_PROGRESS, userId)){
            TaskDto taskDto = modelMapper.map(task, TaskDto.class);
            taskDto.setEnvironment(environment.getProperty("local.server.port"));
            taskDtoList.add(taskDto);
        }
        return taskDtoList;
    }

    @GetMapping("/done")
    public List<TaskDto> getInPlannedTask(@PathVariable("id") int userId){
        List<TaskDto> taskDtoList = new ArrayList<>();
        for(Task task : taskServiceImpl.getTasksByStatus(Status.DONE, userId)){
            TaskDto taskDto = modelMapper.map(task, TaskDto.class);
            taskDto.setEnvironment(environment.getProperty("local.server.port"));
            taskDtoList.add(taskDto);
        }
        return taskDtoList;
    }
}
