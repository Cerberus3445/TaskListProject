package rest.microservices.tasklistapiclone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.microservices.tasklistapiclone.domain.task.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

}
