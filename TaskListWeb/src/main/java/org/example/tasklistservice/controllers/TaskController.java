package org.example.tasklistservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.tasklistservice.client.TaskRestClient;
import org.example.tasklistservice.domain.task.Status;
import org.example.tasklistservice.domain.task.Task;
import org.example.tasklistservice.dto.TaskDto;
import org.example.tasklistservice.exception.ErrorHandling;
import org.example.tasklistservice.exception.TaskNotCreatedException;
import org.example.tasklistservice.exception.TaskNotUpdatedException;
import org.example.tasklistservice.security.PersonDetails;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/web/user/aboutUser/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskRestClient taskRestClient;

    private final ModelMapper modelMapper;

    private final ErrorHandling errorHandling;

    @GetMapping
    public String getTasksByUserId(Model model){
        List<Task> taskDtoList = taskRestClient.getTasksByUserId(getUserId());

        if(taskDtoList.isEmpty()){
            model.addAttribute("noTasks", taskDtoList);
        }

        model.addAttribute("tasks",taskDtoList);
        return "task/list";
    }

    @GetMapping("/{taskId}")
    public String getTaskById(@PathVariable("taskId") int taskId, Model model){
        model.addAttribute("task", taskRestClient.getTask(getUserId(), taskId));
        model.addAttribute("status", taskRestClient.allStatus());
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
        TaskDto taskDto = modelMapper.map(task, TaskDto.class);
        model.addAttribute("task", taskDto);
        return "task/updateTask";
    }

    @PostMapping("/{taskId}/update")
    public String updateTask(@PathVariable("taskId") int taskId, @ModelAttribute("task") TaskDto taskDto, Model model){
        taskDto.setId(taskId);
        try {
            taskDto.setExpirationDate(taskRestClient.formatStringToLocalDataTime(taskDto.getExpirationDateString()));
            taskRestClient.updateTask(getUserId(), taskDto);
        } catch (TaskNotUpdatedException taskNotUpdatedException){
            model.addAttribute("error", errorHandling.taskNotUpdateException(taskNotUpdatedException));
            return "task/updateTask";
        } catch (NumberFormatException e){
            model.addAttribute("error", "Формат даты некорректен. Правильный формат: 2024-06-30 16:30. Где сначала идёт год, месяц, дата, часы, минуты");
            return "task/createTask";
        }
        return "redirect:/web/user/aboutUser/tasks";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute("task") TaskDto taskDto, Model model){
       try {
           taskDto.setExpirationDate(taskRestClient.formatStringToLocalDataTime(taskDto.getExpirationDateString()));
           taskRestClient.createTask(getUserId(), taskDto);
       } catch (TaskNotCreatedException taskNotCreatedException){
            model.addAttribute("error", errorHandling.taskNotCreatedException(taskNotCreatedException));
            return "task/createTask";
        } catch (NumberFormatException e){
           model.addAttribute("error", "Формат даты некорректен. Правильный формат: 2024-06-30 16:30. Где сначала идёт год, месяц, дата, часы, минуты");
           return "task/createTask";
       }
        return "redirect:/web/user/aboutUser/tasks";
    }

    @PostMapping("/{taskId}/status/planned")
    public String changeStatusToPlanned(@PathVariable("taskId") int taskId){
        taskRestClient.setTaskStatus(getUserId(), taskId, Status.PLANNED);
        return "redirect:/web/user/aboutUser/tasks/%d".formatted(taskId);
    }

    @PostMapping("/{taskId}/status/in_progress")
    public String changeStatusToInProgress(@PathVariable("taskId") int taskId){
        taskRestClient.setTaskStatus(getUserId(), taskId, Status.IN_PROGRESS);
        return "redirect:/web/user/aboutUser/tasks/%d".formatted(taskId);
    }

    @PostMapping("/{taskId}/status/done")
    public String changeStatusToDone(@PathVariable("taskId") int taskId){
        taskRestClient.setTaskStatus(getUserId(), taskId, Status.DONE);
        return "redirect:/web/user/aboutUser/tasks/%d".formatted(taskId);
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
