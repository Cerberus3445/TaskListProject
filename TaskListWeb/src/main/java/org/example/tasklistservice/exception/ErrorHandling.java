package org.example.tasklistservice.exception;

import org.springframework.stereotype.Component;

@Component
public class ErrorHandling {

    public String taskNotUpdateException(TaskNotUpdatedException taskNotUpdatedException){
        String string = taskNotUpdatedException.toString();
        string = string.replace("org.example.tasklistservice.exception.TaskNotUpdatedException", "");
        string = string.replace("title", "Название");
        string = string.replace("expirationDate", "Дата истечения срока");
        string = string.replace("description", "Описание");
        string = string.replace(":", "");
        string = string.replace(";", " ");
        string = string.replace("{", "");
        string = string.replace("}", "");
        string = string.replace("\"", "");
        string = string.replace("400", "");
        string = string.replace("message", "");
        string = string.replace(",errorsnull", "");
        String[] array = string.split(" ");

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < array.length; i++){
            stringBuilder.append(array[i]);
            stringBuilder.append(" ");
        }

        return string;
    }

    public String taskNotCreatedException(TaskNotCreatedException taskNotCreatedException){
        String string = taskNotCreatedException.toString();
        string = string.replace("org.example.tasklistservice.exception.TaskNotCreatedException", "");
        string = string.replace("title", "Название");
        string = string.replace("expirationDate", "Дата истечения срока");
        string = string.replace("description", "Описание");
        string = string.replace(":", "");
        string = string.replace(";", " ");
        string = string.replace("{", "");
        string = string.replace("}", "");
        string = string.replace("\"", "");
        string = string.replace("400", "");
        string = string.replace("message", "");
        string = string.replace(",errorsnull", "");
        String[] array = string.split(" ");

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < array.length; i++){
            stringBuilder.append(array[i]);
            stringBuilder.append(" ");
        }

        return string;
    }
}
