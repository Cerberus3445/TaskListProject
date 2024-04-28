package org.example.tasklist.domain.task;

import jakarta.persistence.*;
import lombok.Data;
import org.example.tasklist.domain.user.User;

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
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "expiration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime expirationDate;
}
