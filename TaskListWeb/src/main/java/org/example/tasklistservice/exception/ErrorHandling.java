package org.example.tasklistservice.exception;

import org.springframework.stereotype.Component;

@Component
public class ErrorHandling {

    public String handleTaskException(TaskException taskException){
        String string = taskException.getMessage();
        string = string.replace("title", "Название");
        string = string.replace("expirationDate", "Дата истечения срока");
        string = string.replace("description", "Описание");
        string = removeEverythingUnnecessary(string);
        return string;
    }

    public String handleUserException(UserException userException){
        String string = userException.getMessage();
        string = string.replace("name", "Имя");
        string = string.replace("email", "Email");
        string = string.replace("password", "Паролт");
        string = removeEverythingUnnecessary(string);
        return string;
    }

    private String removeEverythingUnnecessary(String string){
        string = string.replace("[", "");
        string = string.replace("]", "");
        string = string.replace("\"", "");
        string = string.replace("400", "");
        string = string.replace("{", "");
        string = string.replace("}", "");
        string = string.replace("message", "");
        string = string.replace(":", "");
        return string;
    }
}
