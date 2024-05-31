package org.example.tasklistservice.domain.quote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quote {

    private Integer id;

    private String text;

    private String author;

    @Override
    public String toString() {
        return text + " " + author;
    }
}
