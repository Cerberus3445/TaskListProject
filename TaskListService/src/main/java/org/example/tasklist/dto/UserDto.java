package org.example.tasklist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "User Dto")
public class UserDto {

    @Schema(description = "User name", example = "Mike")
    private String name;

    @Schema(description = "User email", example = "elonMask@gmail.com")
    private String email;

    @Schema(description = "User password", example = "dasdasdafsdfkdsfj")
    private String password;
}
