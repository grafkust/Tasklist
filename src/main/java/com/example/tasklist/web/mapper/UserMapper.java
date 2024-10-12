package com.example.tasklist.web.mapper;

import com.example.tasklist.domain.dto.user.UserDto;
import com.example.tasklist.domain.model.user.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper  {

    private final ModelMapper modelMapper;

    public UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }


    public User toEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }


}
