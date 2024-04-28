package org.example.tasklist.dto;

import lombok.Data;
import org.example.tasklist.domain.task.Status;

@Data
public class StatusDto {

    private Status status;
}
