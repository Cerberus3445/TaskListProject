package rest.microservices.tasklistapiclone.services.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest.microservices.tasklistapiclone.domain.exception.TaskException;
import rest.microservices.tasklistapiclone.domain.exception.TaskNotFoundException;
import rest.microservices.tasklistapiclone.domain.task.Status;
import rest.microservices.tasklistapiclone.domain.task.Task;
import rest.microservices.tasklistapiclone.domain.user.User;
import rest.microservices.tasklistapiclone.repositories.TaskRepository;
import rest.microservices.tasklistapiclone.services.TaskService;
import rest.microservices.tasklistapiclone.util.MaxValue;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final UserServiceImpl userServiceImpl;

    private final TaskRepository taskRepository;

    private final MaxValue maxValue;

    private Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Override
    public Task createTask(int userId, Task task){
        logger.info("Create task for user with %d id: ".formatted(userId) + task);
        User user = userServiceImpl.getUser(userId);
        task.setUser(user);
        if(user.getTasks().size() > maxValue.getTask()) throw new TaskException("Превышено максимальное количество заданий");
        task.setStatus(Status.PLANNED);
        Task createdTask = taskRepository.save(task);
        return createdTask;
    }

    @Override
    public Task getTaskById(int id){
        logger.info("Get task with %d id".formatted(id));
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    @Override
    public Task updateTask(int taskId, Task updatedTask){
        logger.info("Update task with %d id: ".formatted(taskId) + updatedTask);
        Task task = getTaskById(taskId);
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        task.setExpirationDate(updatedTask.getExpirationDate());
        taskRepository.save(task);
        return task;
    }

    @Override
    public void changeStatus(int taskId, Status status){
        logger.info("Change status task with %d id: " + status);
        Task task = getTaskById(taskId);
        task.setStatus(status);
    }

    @Override
    public void deleteTask(int id){
        logger.info("Delete task with %d id".formatted(id));
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getTasksByStatus(Status status, int userId) {
        logger.info("Get user task by status. User id = %d ".formatted(userId) + status);
        List<Task> taskList = userServiceImpl.getUserTasks(userServiceImpl.getUser(userId));
        List<Task> requiredTasks = new ArrayList<>();
        for(Task task : taskList){
            if(task.getStatus() == status){
                requiredTasks.add(task);
            }
        }
        return requiredTasks;
    }
}
