package org.example.tasklist.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("tasklist-backend")
@Data
public class MaxValue {

    private int quote;

    private int task;
}
