package org.example.tasklistservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.tasklistservice.domain.task.Status;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private int id;

    private String title;

    private String description;

    private Status status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime expirationDate;

    private String expirationDateString; //в это поле с html летит дата(обычная строка), а потом её обрабатываем в LocalDateTime expirationDate
}
