package org.example.tasklist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
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

    @Schema(description = "User password", example = "dasdasdafsdfkdsfj")
    @Size(min = 5, max = 40, message = "Пароль должен составлять от 5 до 40 символов")
    private String password;
}