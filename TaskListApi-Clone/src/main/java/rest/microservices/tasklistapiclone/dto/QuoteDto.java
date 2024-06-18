package rest.microservices.tasklistapiclone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuoteDto {

    private int id;

    @NotEmpty(message = "Текст цитаты не должен быть пустым")
    private String text;

    @NotEmpty(message = "Текст цитаты не должен быть пустым")
    private String author;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String environment;
}
