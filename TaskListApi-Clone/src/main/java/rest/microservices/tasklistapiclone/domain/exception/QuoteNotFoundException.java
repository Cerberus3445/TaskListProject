package rest.microservices.tasklistapiclone.domain.exception;

public class QuoteNotFoundException extends RuntimeException{

    public QuoteNotFoundException(String message) {
        super(message);
    }
}
