package com.example.tasklist.web.mapper;

import com.example.tasklist.domain.dto.task.TaskDto;
import com.example.tasklist.domain.model.task.Task;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskMapper  {

    private final ModelMapper modelMapper;

    public TaskDto toDto(Task task) {
        return modelMapper.map(task, TaskDto.class);
    }

    public List<TaskDto> toDto(List<Task> tasks) {

        return tasks
                .stream()
                .map(task -> modelMapper.map(task, TaskDto.class))
                .toList();
    }

    public Task toEntity(TaskDto dto) {
        return modelMapper.map(dto, Task.class);
    }



}
