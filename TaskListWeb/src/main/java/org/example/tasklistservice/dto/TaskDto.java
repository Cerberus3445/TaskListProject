package org.example.tasklistservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.tasklistservice.domain.task.Status;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private int id;

    private String title;

    private String description;

    private Status status;

    private String expirationDate;
}
