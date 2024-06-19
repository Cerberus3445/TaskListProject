package org.example.tasklistservice.client;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.example.tasklistservice.domain.task.Status;
import org.example.tasklistservice.domain.task.Task;
import org.example.tasklistservice.dto.TaskDto;
import org.example.tasklistservice.exception.TaskException;
import org.example.tasklistservice.proxy.FeignProxy;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class TaskRestClient {

    private final UserRestClient userRestClient;

    private final Logger logger = LoggerFactory.getLogger(TaskRestClient.class);

    private final FeignProxy feignProxy;

    private final ModelMapper modelMapper;

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public Task getTask(int userId, int taskId){
        logger.info("Get task where userId = %d and taskId = %d".formatted(userId, taskId));
        try {
            TaskDto taskDto = feignProxy.getTask(userId, taskId);
            return modelMapper.map(taskDto, Task.class);
        } catch (FeignException e){
            throw new TaskException(e.getMessage());
        }
    }

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public List<Task> getDoneTasks(int userId){
        logger.info("Get task with DONE status userId = %d".formatted(userId));
        try {
            List<TaskDto> taskDtoList = feignProxy.getTasksWithDoneStatus(userId);
            return fromTaskDtoArrayToTaskList(taskDtoList, userId);
        } catch (FeignException e){
            throw new TaskException(e.getMessage());
        }
    }

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public List<Task> getInProgressTasks(int userId){
        logger.info("Get task with IN_PROGRESS status userId = %d".formatted(userId));
        try {
            List<TaskDto> taskDtoList = feignProxy.getTasksWithInProgressStatus(userId);
            return fromTaskDtoArrayToTaskList(taskDtoList, userId);
        } catch (FeignException e){
            throw new TaskException(e.getMessage());
        }
    }

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public List<Task> getPlannedTasks(int userId){
        logger.info("Get task with PLANNED status userId = %d".formatted(userId));
        try {
            List<TaskDto> taskDtoList = feignProxy.getTasksWithPlannedStatus(userId);
            return fromTaskDtoArrayToTaskList(taskDtoList, userId);
        } catch (FeignException e){
            throw new TaskException(e.getMessage());
        }
    }

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public List<Task> getTasksByUserId(int id){
        logger.info("Get tasks with id %d".formatted(id));
        try {
            List<TaskDto> taskDtoList = feignProxy.getTasksList(id);
            return fromTaskDtoArrayToTaskList(taskDtoList, id);
        } catch (FeignException e){
            throw new TaskException(e.getMessage());
        }
    }

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public void createTask(int userId, TaskDto taskDto){
        logger.info("Create task " + taskDto.toString() + "where userId  = %d".formatted(userId));
        try {
            feignProxy.createTask(userId, taskDto);
        } catch (FeignException e){
            throw new TaskException(e.getMessage());
        }
    }

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public void updateTask(int userId, int taskId, TaskDto taskDto){
        logger.info("Update task " + taskDto.toString() + "where userId = %d and taskId = %d".formatted(userId, taskId));
        try {
            Task task = getTask(userId, taskId);
            taskDto.setStatus(task.getStatus());
            feignProxy.updateTask(userId, taskId, taskDto);
        } catch (FeignException e){
            throw new TaskException(e.getMessage());
        }
    }

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public void deleteTask(int userId, int taskId){
        logger.info("Delete task where userId = %d and taskId = %d".formatted(userId, taskId));
        try {
            feignProxy.deleteTask(userId, taskId);
        } catch (FeignException e){
            throw new TaskException(e.getMessage());
        }
    }

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public void setTaskStatus(int userId, int taskId, Status status){
        logger.info("Set task status with id = %d ".formatted(taskId) + status.toString());
        try {
            feignProxy.changeStatus(userId, taskId, status.toString());
        } catch (FeignException e){
            throw new TaskException(e.getMessage());
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

        return LocalDateTime.of(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4));
    }

    public String formatTaskStatusToString(Status status){
        switch (status){
            case DONE -> {
                return "СДЕЛАНО";
            } case PLANNED -> {
                return "ЗАПЛАНИРОВАННО";
            } case IN_PROGRESS -> {
                return "В ПРОЦЕССЕ";
            }default -> {
                return "ОШИБКА";
            }
        }
    }

    public String formatDateToString(LocalDateTime localDateTime){
        return localDateTime.toString().replace("T", " ");
    }

    private List<Task> fromTaskDtoArrayToTaskList(List<TaskDto> taskDtoArray, int userId){
        List<Task> taskList = new ArrayList<>();
        for(TaskDto taskDto : taskDtoArray){
            Task task = modelMapper.map(taskDto, Task.class);
            task.setUser(userRestClient.getUser(userId));
            taskList.add(task);
        }
        return taskList;
    }

    public List<Status> allStatus(){
        List<Status> statusList = new ArrayList<>();
        statusList.add(Status.PLANNED);
        statusList.add(Status.IN_PROGRESS);
        statusList.add(Status.DONE);
        return statusList;
    }
}
