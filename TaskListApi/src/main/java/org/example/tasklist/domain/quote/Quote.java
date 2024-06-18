package org.example.tasklist.domain.quote;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "quote")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "text")
    private String text;

    @Column(name = "author")
    private String author;
}
