package org.example.tasklistservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.tasklistservice.client.TaskRestClient;
import org.example.tasklistservice.domain.task.Task;
import org.example.tasklistservice.dto.TaskDto;
import org.example.tasklistservice.security.PersonDetails;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web/user/aboutUser/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskRestClient taskRestClient;

    private final ModelMapper modelMapper;

    @GetMapping
    public String getTasksByUserId(Model model){
        model.addAttribute("tasks", taskRestClient.getTasksByUserId(getUserId()));
        return "task/list";
    }

    @GetMapping("/{taskId}")
    public String getTaskById(@PathVariable("taskId") int taskId, Model model){
        model.addAttribute("task", taskRestClient.getTask(getUserId(), taskId));
        return "task/aboutTask";
    }

    @GetMapping("/create")
    public String createTaskPage(Model model){
        model.addAttribute("task", new TaskDto());
        return "task/createTask";
    }

    @GetMapping("/{taskId}/update")
    public String updateTaskPage(@PathVariable("taskId") int taskId, Model model){
        Task task = taskRestClient.getTask(getUserId(), taskId);
        TaskDto taskDto = taskRestClient.toTaskDto(task);
        model.addAttribute("task", taskDto);
        return "task/updateTask";
    }

    @PostMapping("/{taskId}/update")
    public String updateTask(@PathVariable("taskId") int taskId, @ModelAttribute("task") TaskDto taskDto){
        taskDto.setId(taskId);
        taskRestClient.updateTask(getUserId(), taskDto);
        return "redirect:/web/user/aboutUser/tasks";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute("task") TaskDto taskDto){
        taskRestClient.createTask(getUserId(), taskDto);
        return "redirect:/web/user/aboutUser/tasks";
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
