package com.example.tasklist.domain.dto.task;

import com.example.tasklist.domain.dto.validation.OnCreate;
import com.example.tasklist.domain.dto.validation.OnUpdate;
import com.example.tasklist.domain.model.task.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Setter @Getter
public class TaskDto {

    @Schema(example = "1")
    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @Schema(example = "Spring")
    @NotNull(message = "Title must be not null.")
    @Length(max = 255, message = "Title length must be smaller than 255 symbols.")
    private String title;

    @Schema(example = "Write Spring API application")
    @Length(max = 255,message = "Description length must be smaller than 255 symbols.")
    private String description;

    @Schema(example = "TODO")
    private Status status;

    @Schema(example = "2024-10-10 10:10")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime expirationDate;

}
