package org.example.tasklist.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDto {

    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Server port", example = "8080/8081")
    private String environment;
}
