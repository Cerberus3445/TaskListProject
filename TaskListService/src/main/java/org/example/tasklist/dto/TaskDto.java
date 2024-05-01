package org.example.tasklist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.example.tasklist.domain.task.Status;

@Data
@Schema(description = "Task Dto")
public class TaskDto {

    @Schema(description = "Task id", example = "1")
    private int id;

    @Schema(description = "Task title", example = "Eat chicken")
    private String title;

    @Schema(description = "Task description", example = "Can be empty")
    private String description;

    @Schema(description = "Task status", example = "PLANNED/IN_PROGRESS/DONE")
    private Status status;

    @Schema(description = "Task expiration date", example = "2024-04-30-16-30 where 2024-year, 04-month, 30-day, 16-hours, 30-minutes")
    private String expirationDate;
}
