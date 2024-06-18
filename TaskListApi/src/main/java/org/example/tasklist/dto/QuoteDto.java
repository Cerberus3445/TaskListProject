package org.example.tasklist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Server port", example = "8080/8081")
    private String environment;
}
