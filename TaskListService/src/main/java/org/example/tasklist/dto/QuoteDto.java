package org.example.tasklist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description = "Quote Dto")
@AllArgsConstructor
@NoArgsConstructor
public class QuoteDto {

    private int id;

    @NotEmpty(message = "Текст цитаты не должен быть пустым")
    private String text;

    @NotEmpty(message = "Текст цитаты не должен быть пустым")
    private String author;
}
