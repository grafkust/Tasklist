package com.example.tasklist.web.controller;

import com.example.tasklist.domain.dto.task.TaskDto;
import com.example.tasklist.domain.dto.user.UserDto;
import com.example.tasklist.domain.dto.validation.OnCreate;
import com.example.tasklist.domain.dto.validation.OnUpdate;
import com.example.tasklist.domain.model.task.Task;
import com.example.tasklist.domain.model.user.User;
import com.example.tasklist.service.TaskService;
import com.example.tasklist.service.UserService;
import com.example.tasklist.web.mapper.TaskMapper;
import com.example.tasklist.web.mapper.UserMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User Controller")
public class UserController {

    private final UserService userService;
    private final TaskService taskService;
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    @GetMapping("/{id}")
    @PreAuthorize("@cse.canAccessUser(#id)")
    public UserDto getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @PostMapping("/{id}/tasks")
    @PreAuthorize("@cse.canAccessUser(#id)")
    public TaskDto createTask(@PathVariable Long id,
                              @Validated(OnCreate.class) @RequestBody TaskDto taskDto) {

        Task newTask = taskMapper.toEntity(taskDto);

        taskService.create(newTask, id);

        return taskMapper.toDto(newTask);
    }

    @PutMapping
    @PreAuthorize("@cse.canAccessUser(#userDto.id)")
    public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto userDto) {

        User user = userMapper.toEntity(userDto);
        User updatedUser = userService.update(user);
        return userMapper.toDto(updatedUser);
    }

    @GetMapping("/{id}/tasks")
    @PreAuthorize("@cse.canAccessUser(#id)")
    public List<TaskDto> getTasksByUserId(@PathVariable Long id) {
        List<Task> tasks = taskService.getAllByUserId(id);
        return taskMapper.toDto(tasks);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("@cse.canAccessUser(#userId)")
    public void deleteById(@PathVariable Long userId) {
        userService.delete(userId);
    }


}
