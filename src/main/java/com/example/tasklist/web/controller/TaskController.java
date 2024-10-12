package com.example.tasklist.web.controller;

import com.example.tasklist.domain.dto.task.TaskDto;
import com.example.tasklist.domain.dto.task.TaskImageDto;
import com.example.tasklist.domain.dto.validation.OnUpdate;
import com.example.tasklist.domain.model.task.Task;
import com.example.tasklist.domain.model.task.TaskImage;
import com.example.tasklist.service.TaskService;
import com.example.tasklist.web.mapper.TaskMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Validated
@Tag(name = "Task Controller")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    @PreAuthorize("@cse.canAccessTask(#id)")
    public TaskDto getById(@PathVariable Long id) {
        Task task = taskService.getById(id);
        return taskMapper.toDto(task);
    }

    @PutMapping
    @PreAuthorize("@cse.canAccessTask(#dto.id)")
    public TaskDto update(@Validated(OnUpdate.class) @RequestBody TaskDto dto) {

        Task task = taskMapper.toEntity(dto);
        Task updatedTask = taskService.update(task);
        return taskMapper.toDto(updatedTask);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@cse.canAccessTask(#id)")
    public void deleteById(@PathVariable Long id) {
        taskService.delete(id);
    }

    @PostMapping("/{id}/image")
    @PreAuthorize("@cse.canAccessTask(#id)")
    public void uploadImage(@PathVariable Long id, @Validated @ModelAttribute TaskImageDto imageDto) throws IOException {

        TaskImage image = modelMapper.map(imageDto,TaskImage.class);
        taskService.uploadImage(id, image);
    }

}
