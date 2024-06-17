package org.example.tasklist.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.tasklist.domain.exception.TaskException;
import org.example.tasklist.domain.task.Status;
import org.example.tasklist.domain.task.Task;
import org.example.tasklist.dto.TaskDto;
import org.example.tasklist.services.impl.TaskServiceImpl;
import org.example.tasklist.services.impl.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/tasklist-api/user/{id}/tasks")
@Tag(name = "Task Controller", description = "Task API")
public class TaskController {

    private final UserServiceImpl userServiceImpl;

    private final TaskServiceImpl taskServiceImpl;

    private final ModelMapper modelMapper;

    @GetMapping
    @Operation(summary = "Get user tasks by their id")
    public List<TaskDto> getTasksByUserId(@PathVariable("id") int id){
        List<Task> taskList = userServiceImpl.getUserTasks(userServiceImpl.getUser(id));
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
        Task task = taskServiceImpl.getTaskById(taskId);
        TaskDto taskDto = modelMapper.map(task, TaskDto.class);
        return taskDto;
    }

    @PostMapping
    @Operation(summary = "Create task and assign it to the user")
    public HttpStatus createTaskDto(@PathVariable("id") int id, @RequestBody @Valid TaskDto taskDto, BindingResult bindingResult){
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
        return HttpStatus.CREATED;
    }

    @PostMapping("/{taskId}/update")
    @Operation(summary = "Update task and assign it to the user")
    public HttpStatus updateTask(@PathVariable("taskId") int id, @RequestBody @Valid TaskDto taskDto, BindingResult bindingResult){
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
        return HttpStatus.OK;
    }

    @PostMapping("/{taskId}/status")
    @Operation(summary = "Change task status")
    public HttpStatus changeTaskStatus(@PathVariable("taskId") int taskId, @RequestParam("status") String status){
        taskServiceImpl.changeStatus(taskId, Status.valueOf(status));
        return HttpStatus.OK;
    }

    @DeleteMapping("/{taskId}")
    @Operation(summary = "Delete task by their id")
    public HttpStatus deleteTaskById(@PathVariable("taskId") int taskId){
        taskServiceImpl.deleteTask(taskId);
        return HttpStatus.OK;
    }

    @GetMapping("/planned")
    @Operation(summary = "Get tasks with PLANNED status")
    public List<TaskDto> getPlannedTask(@PathVariable("id") int userId){
        List<TaskDto> taskDtoList = new ArrayList<>();
        for(Task task : taskServiceImpl.getTasksByStatus(Status.PLANNED, userId)){
            taskDtoList.add(modelMapper.map(task, TaskDto.class));
        }
        return taskDtoList;
    }

    @GetMapping("/in_progress")
    @Operation(summary = "Get tasks with IN_PROGRESS status")
    public List<TaskDto> getInProgressTask(@PathVariable("id") int userId){
        List<TaskDto> taskDtoList = new ArrayList<>();
        for(Task task : taskServiceImpl.getTasksByStatus(Status.IN_PROGRESS, userId)){
            taskDtoList.add(modelMapper.map(task, TaskDto.class));
        }
        return taskDtoList;
    }

    @GetMapping("/done")
    @Operation(summary = "Get tasks with DONE status")
    public List<TaskDto> getInPlannedTask(@PathVariable("id") int userId){
        List<TaskDto> taskDtoList = new ArrayList<>();
        for(Task task : taskServiceImpl.getTasksByStatus(Status.DONE, userId)){
            taskDtoList.add(modelMapper.map(task, TaskDto.class));
        }
        return taskDtoList;
    }
}
