package org.example.tasklistservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.tasklistservice.client.TaskRestClient;
import org.example.tasklistservice.domain.task.Task;
import org.example.tasklistservice.dto.TaskDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/web/user/{id}/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskRestClient taskRestClient;


    @GetMapping
    public String getTasksByUserId(@PathVariable("id") int id, Model model){
        model.addAttribute("tasks", taskRestClient.getTasksByUserId(id));
        return "task/list";
    }

    @GetMapping("/{taskId}")
    public String getTaskById(@PathVariable("id") int userId, @PathVariable("taskId") int taskId, Model model){
        model.addAttribute("task", taskRestClient.getTask(userId, taskId));
        return "task/aboutTask";
    }

    @GetMapping("/create")
    public String createTaskPage(Model model){
        model.addAttribute("task", new Task());
        return "task/createTask";
    }

    @DeleteMapping("/{taskId}")
    public String deleteTask(@PathVariable("id") int userId, @PathVariable("taskId") int taskId){
        taskRestClient.deleteTask(userId, taskId);
        return "redirect:/web/user/%d/tasks".formatted(userId);
    }
}
