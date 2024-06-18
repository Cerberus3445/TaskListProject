package rest.microservices.tasklistapiclone.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import rest.microservices.tasklistapiclone.domain.task.Status;

import java.time.LocalDateTime;

@Data
public class TaskDto {

    private int id;

    @Length(min = 2, max = 50, message = "Длина названия должна составлять от 2 до 50 символов")
    private String title;

    @Length(min = 2, max = 20, message = "Длина описания должна составлять от 2 до 20 символов")
    private String description;

    private Status status;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime expirationDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String environment;
}