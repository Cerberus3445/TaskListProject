package org.example.tasklist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.example.tasklist.domain.task.Status;

@Data
@Schema(description = "Status Dto")
public class StatusDto {

    @Schema(description = "Task status", example = "PLANNED, IN_PROGRESS, DONE")
    private Status status;
}
