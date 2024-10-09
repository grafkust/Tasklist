package com.example.tasklist.web.mapper;

import com.example.tasklist.domain.dto.task.TaskDto;
import com.example.tasklist.domain.model.task.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper extends Mappable<Task, TaskDto> {

}
