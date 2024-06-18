package rest.microservices.tasklistapiclone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDto {

    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String environment;
}
