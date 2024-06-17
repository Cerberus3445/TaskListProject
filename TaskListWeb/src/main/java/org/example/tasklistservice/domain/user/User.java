package org.example.tasklistservice.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.tasklistservice.domain.task.Task;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;

    @Length(min = 2, max = 20, message = "Длина имени должна составлять от 2 до 20 символов")
    private String name;

    @Email(message = "Некорректный email")
    private String email;

    @Size(min = 5, max = 40, message = "Пароль должен составлять от 5 до 40 символов")
    private String password;

    private List<Task> tasks;

    private String role;
}
