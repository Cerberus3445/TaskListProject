package org.example.tasklistservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.tasklistservice.client.TaskRestClient;
import org.example.tasklistservice.client.UserRestClient;
import org.example.tasklistservice.domain.task.Task;
import org.example.tasklistservice.security.PersonDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/user/aboutUser/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskRestClient taskRestClient;

    private final UserRestClient userRestClient;

    @GetMapping
    public String getTasksByUserId(Model model){
        model.addAttribute("user", userRestClient.getUser(getUserId()));
        model.addAttribute("tasks", taskRestClient.getTasksByUserId(getUserId()));
        return "task/list";
    }

    @GetMapping("/{taskId}")
    public String getTaskById(@PathVariable("taskId") int taskId, Model model){
        model.addAttribute("user", userRestClient.getUser(getUserId()));
        model.addAttribute("task", taskRestClient.getTask(getUserId(), taskId));
        return "task/aboutTask";
    }

    @GetMapping("/create")
    public String createTaskPage(Model model){
        model.addAttribute("user", userRestClient.getUser(getUserId()));
        model.addAttribute("task", new Task());
        return "task/createTask";
    }

    @DeleteMapping("/{taskId}")
    public String deleteTask(@PathVariable("taskId") int taskId){
        taskRestClient.deleteTask(getUserId(), taskId);
        return "redirect:/web/user/aboutUser/tasks";
    }

    public int getUserId(){
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return personDetails.getUserId();
    }
}
