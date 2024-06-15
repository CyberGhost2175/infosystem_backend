package com.example.JSnote.mappers;

import com.example.JSnote.dtos.SignUpDto;
import com.example.JSnote.dtos.UserDto;
import com.example.JSnote.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);

}
