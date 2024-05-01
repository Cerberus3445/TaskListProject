package org.example.tasklist.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.tasklist.domain.task.Task;
import org.example.tasklist.domain.user.User;
import org.example.tasklist.dto.StatusDto;
import org.example.tasklist.dto.TaskDto;
import org.example.tasklist.services.TaskService;
import org.example.tasklist.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
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
            taskDto.setExpirationDate(taskService.convertLocalDataTimeFromTaskDtoToString(taskDto.getExpirationDate()));
            taskDtoList.add(taskDto);
        }
        return taskDtoList;
    }

    @GetMapping("/{taskId}")
    @Operation(summary = "Get task by their id")
    public TaskDto getTaskById(@PathVariable("taskId") int taskId){
        Task task = taskService.getTaskById(taskId);
        TaskDto taskDto = modelMapper.map(task, TaskDto.class);
        taskDto.setExpirationDate(taskService.convertLocalDataTimeFromTaskDtoToString(taskDto.getExpirationDate()));
        return taskDto;
    }

    @PostMapping("/create")
    @Operation(summary = "Create a task and assign it to the user")
    public HttpStatus createTaskDto(@PathVariable("id") int id, @RequestBody TaskDto taskDto){
        Task task = modelMapper.map(taskDto, Task.class);
        LocalDateTime date = taskService.formatStringToLocalDataTime(taskDto.getExpirationDate());
        task.setExpirationDate(date);
        taskService.createTask(id, task);
        return HttpStatus.CREATED;
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
