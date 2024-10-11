package com.example.tasklist.domain.model.jwt;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequest {


    @NotNull(message = "Username must be not null.")
    @Schema(example = "user-1@mail.ru")
    private String username;


    @NotNull(message = "Password must be not null.")
    @Schema(example = "12345")
    private String password;

}
