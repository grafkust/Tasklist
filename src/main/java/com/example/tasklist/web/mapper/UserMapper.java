package com.example.tasklist.web.mapper;

import com.example.tasklist.domain.dto.user.UserDto;
import com.example.tasklist.domain.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto> {
}
