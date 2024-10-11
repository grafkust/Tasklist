package com.example.tasklist.domain.dto.user;

import com.example.tasklist.domain.dto.validation.OnCreate;
import com.example.tasklist.domain.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


@Getter
@Setter
public class UserDto {

    @Schema(example = "1")
    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @Schema(example = "user-1")
    @NotNull(message = "Name must be not null.")
    @Length(max = 255, message = "Name length must be smaller than 255 symbols.")
    private String name;

    @Schema(example = "user-1@mail.ru")
    @NotNull(message = "Username must be not null.")
    @Length(max = 255, message = "Username length must be smaller than 255 symbols.")
    private String username;

    @Schema(example = "12345")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must be not null.")
    private String password;

    @Schema(example = "12345")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password confirmation must be not null.", groups = {OnCreate.class})
    private String passwordConfirmation;


}
