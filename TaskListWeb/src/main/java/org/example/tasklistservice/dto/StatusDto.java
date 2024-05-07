package org.example.tasklistservice.dto;

import lombok.Data;
import org.example.tasklistservice.domain.task.Status;

@Data
public class StatusDto {

    private Status status;
}
