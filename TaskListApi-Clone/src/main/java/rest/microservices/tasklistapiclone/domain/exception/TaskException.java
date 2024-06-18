package rest.microservices.tasklistapiclone.domain.exception;

public class TaskException extends RuntimeException{

    public TaskException(String message) {
        super(message);
    }
}
