package org.example.tasklistservice.domain.task;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.example.tasklistservice.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "title")
    @NotEmpty(message = "Название не может равняться 0")
    @Length(max = 40, message = "Максимальная длина должна равняться 40 символов")
    private String title;

    @Column(name = "description")
    @Length(max = 1000, message = "Максимальная длина описания должна равняться 1000 символов")
    private String description;

    @Column(name = "status")
    @NotNull
    private Status status;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
}
