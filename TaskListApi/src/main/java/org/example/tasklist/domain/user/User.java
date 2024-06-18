package org.example.tasklist.domain.user;

import jakarta.persistence.*;
import lombok.Data;
import org.example.tasklist.domain.task.Task;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Task> tasks;

    @Column(name = "role")
    private String role;
}