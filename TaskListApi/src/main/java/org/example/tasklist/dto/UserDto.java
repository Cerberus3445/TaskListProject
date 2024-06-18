package org.example.tasklist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@Schema(description = "User Dto")
public class UserDto {

    private int id;

    @Schema(description = "User name", example = "Mike")
    @Length(min = 2, max = 20, message = "Длина имени должна составлять от 2 до 20 символов")
    private String name;

    @Schema(description = "User email", example = "elonMask@gmail.com")
    @Email(message = "Некорректный email")
    private String email;

    @Schema(description = "User password", example = "dasdasdafSDsdfkdsfj")
    @Size(min = 5, max = 120, message = "Длина пароля должен составлять от 5 до 120 символов")
    private String password;

    @Schema(description = "User role", example = "USER, ADMIN")
    private String role;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Server port", example = "8080/8081")
    private String environment;
}