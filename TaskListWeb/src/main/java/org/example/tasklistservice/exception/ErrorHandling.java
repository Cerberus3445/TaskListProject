package org.example.tasklistservice.exception;

import org.springframework.stereotype.Component;

@Component
public class ErrorHandling {

    public String handleUserAndTaskException(Exception e){
        String[] array = e.getMessage().split(" ");

        int position = 0;
        for(int i = 0; i < array.length; i++){
            if(array[i].equals("-")){
                position = i + 1;
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = position; i < array.length; i++){
            stringBuilder.append(array[i]);
            stringBuilder.append(" ");
        }
        String string = stringBuilder.toString().replace("]", "");
        string = string.replace("}", "");
        string = string.replace("\"","");
        return string;
    }

    public String handleQuoteException(QuoteException e){
        String[] array = e.getMessage().split(" ");

        int position = 0;
        for(int i = 0; i < array.length; i++){
            if(array[i].equals("")){
                position = i + 1;
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = position; i < array.length; i++){
            stringBuilder.append(array[i]);
            stringBuilder.append(" ");
        }
        String string = stringBuilder.toString().replace("]", "");
        return string;
    }
}
