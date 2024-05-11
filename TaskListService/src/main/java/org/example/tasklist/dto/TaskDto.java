package org.example.tasklist.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.tasklist.domain.task.Status;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@Schema(description = "Task Dto")
public class TaskDto {

    @Schema(description = "Task id", example = "1")
    private int id;

    @Schema(description = "Task title", example = "Eat chicken")
    @Length(min = 2, max = 20, message = "Длина названия должна составлять от 2 до 20 символов")
    private String title;

    @Schema(description = "Task description", example = "Can be empty")
    @Length(min = 2, max = 20, message = "Длина описания должна составлять от 2 до 20 символов")
    private String description;

    @Schema(description = "Task status", example = "PLANNED/IN_PROGRESS/DONE")
    private Status status;

    @Schema(description = "Task expiration date", example = "2024-04-30 16:30 where 2024-year, 04-month, 30-day, 16-hours, 30-minutes")
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime expirationDate;
}