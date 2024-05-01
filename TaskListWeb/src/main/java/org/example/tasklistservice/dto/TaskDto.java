package org.example.tasklistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.tasklistservice.domain.task.Status;

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
