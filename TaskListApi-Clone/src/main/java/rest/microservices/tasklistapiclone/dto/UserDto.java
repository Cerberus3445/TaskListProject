package rest.microservices.tasklistapiclone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class UserDto {

    private int id;

    @Length(min = 2, max = 20, message = "Длина имени должна составлять от 2 до 20 символов")
    private String name;

    @Email(message = "Некорректный email")
    private String email;

    @Size(min = 5, max = 120, message = "Длина пароля должен составлять от 5 до 120 символов")
    private String password;

    private String role;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String environment;
}