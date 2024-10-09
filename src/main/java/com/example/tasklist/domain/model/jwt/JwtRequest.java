package com.example.tasklist.domain.model.jwt;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequest {


    @NotNull(message = "Username must be not null.")
    private String username;


    @NotNull(message = "Password must be not null.")
    private String password;

}
