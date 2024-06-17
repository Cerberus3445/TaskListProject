package org.example.tasklistservice.proxy;

import org.example.tasklistservice.config.FeignClientConfiguration;
import org.example.tasklistservice.dto.PasswordDto;
import org.example.tasklistservice.dto.TaskDto;
import org.example.tasklistservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "tasklist-backend", configuration = FeignClientConfiguration.class)
public interface FeignProxy {

    @GetMapping("/v1/tasklist-api/user/{id}")
    UserDto getUser(@PathVariable int id);

    @GetMapping("/v1/tasklist-api/user/byEmail")
    UserDto getUserByEmail(@RequestParam("email") String email);

    @PostMapping("/v1/tasklist-api/user")
    void createUser(@RequestBody UserDto userDto);

    @PostMapping("/v1/tasklist-api/user/{id}/updatePassword")
    void updatePassword(@PathVariable("id") int id, @RequestBody PasswordDto password);

    @PostMapping("/v1/tasklist-api/user/{id}/update")
    void updateUser(@PathVariable("id") int id, @RequestBody UserDto userDto);

    @DeleteMapping("/v1/tasklist-api/user/{id}")
    void deleteUser(@PathVariable("id") int id);

    @GetMapping("/v1/tasklist-api/user/{id}/tasks/{taskId}")
    TaskDto getTask(@PathVariable("id") int userId, @PathVariable("taskId") int taskId);

    @GetMapping("/v1/tasklist-api/user/{id}/tasks")
    List<TaskDto> getTasksList(@PathVariable("id") int userId);

    @GetMapping("/v1/tasklist-api/user/{id}/tasks/planned")
    List<TaskDto> getTasksWithPlannedStatus(@PathVariable("id") int userId);

    @GetMapping("/v1/tasklist-api/user/{id}/tasks/in_progress")
    List<TaskDto> getTasksWithInProgressStatus(@PathVariable("id") int userId);

    @GetMapping("/v1/tasklist-api/user/{id}/tasks/done")
    List<TaskDto> getTasksWithDoneStatus(@PathVariable("id") int userId);

    @PostMapping("/v1/tasklist-api/user/{id}/tasks")
    void createTask(@PathVariable("id") int id, @RequestBody TaskDto taskDto);

    @PostMapping("/v1/tasklist-api/user/{id}/tasks/{taskId}/update")
    void updateTask(@PathVariable("id") int id, @PathVariable("taskId") int taskId, @RequestBody TaskDto taskDto);

    @PostMapping("/v1/tasklist-api/user/{id}/tasks/{taskId}/status")
    void changeStatus(@PathVariable("id") int id, @PathVariable("taskId") int taskId, @RequestParam("status") String status);

    @DeleteMapping("/v1/tasklist-api/user/{id}/tasks/{taskId}")
    void deleteTask(@PathVariable("id") int userId, @PathVariable("taskId") int taskId);
}