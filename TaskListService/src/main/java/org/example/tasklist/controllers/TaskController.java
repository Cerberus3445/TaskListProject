package org.example.tasklist.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.tasklist.domain.exception.TaskNotCreatedException;
import org.example.tasklist.domain.exception.TaskNotUpdatedException;
import org.example.tasklist.domain.task.Task;
import org.example.tasklist.domain.user.User;
import org.example.tasklist.dto.StatusDto;
import org.example.tasklist.dto.TaskDto;
import org.example.tasklist.services.TaskService;
import org.example.tasklist.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/{id}/tasks")
@Tag(name = "Task Controller", description = "Task API")
public class TaskController {

    private final UserService userService;

    private final TaskService taskService;

    private final ModelMapper modelMapper;

    @GetMapping
    @Operation(summary = "Get user tasks by their id")
    public List<TaskDto> getTasksByUserId(@PathVariable("id") int id){
        List<Task> taskList = userService.getUserTasks(userService.showUserById(id));
        List<TaskDto> taskDtoList = new ArrayList<>();
        for(Task task : taskList){
            TaskDto taskDto = modelMapper.map(task, TaskDto.class);
            taskDtoList.add(taskDto);
        }
        return taskDtoList;
    }

    @GetMapping("/{taskId}")
    @Operation(summary = "Get task by their id")
    public TaskDto getTaskById(@PathVariable("taskId") int taskId){
        Task task = taskService.getTaskById(taskId);
        TaskDto taskDto = modelMapper.map(task, TaskDto.class);
        return taskDto;
    }

    @PostMapping("/create")
    @Operation(summary = "Create task and assign it to the user")
    public HttpStatus createTaskDto(@PathVariable("id") int id, @RequestBody @Valid TaskDto taskDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder stringBuilder = new StringBuilder();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                stringBuilder.append(fieldError.getField()).append(" - ").append(fieldError.getDefaultMessage()).append(";");
            }
            throw new TaskNotCreatedException(stringBuilder.toString());
        }
        Task task = modelMapper.map(taskDto, Task.class);
        taskService.createTask(id, task);
        return HttpStatus.CREATED;
    }

    @PostMapping("/{taskId}/update")
    @Operation(summary = "Update task and assign it to the user")
    public HttpStatus updateTask(@PathVariable("taskId") int id, @RequestBody @Valid TaskDto taskDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder stringBuilder = new StringBuilder();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                stringBuilder.append(fieldError.getField()).append(" - ").append(fieldError.getDefaultMessage()).append(";");
            }
            throw new TaskNotUpdatedException(stringBuilder.toString());
        }
        Task task = modelMapper.map(taskDto, Task.class);
        taskService.updateTask(id, task);
        return HttpStatus.OK;
    }

    @PostMapping("/{taskId}/status")
    @Operation(summary = "Change task status")
    public HttpStatus changeTaskStatus(@PathVariable("taskId") int taskId, @RequestBody StatusDto statusDto){
        taskService.changeStatus(taskId, statusDto.getStatus());
        return HttpStatus.OK;
    }

    @DeleteMapping("/{taskId}")
    @Operation(summary = "Delete task by their id")
    public HttpStatus deleteTaskById(@PathVariable("taskId") int taskId){
        taskService.deleteTask(taskId);
        return HttpStatus.OK;
    }
}
