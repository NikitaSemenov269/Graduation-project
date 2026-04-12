package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import ru.yandex.practicum.DTO.user.NewUserRequest;
import ru.yandex.practicum.DTO.user.UserDto;
import ru.yandex.practicum.model.User;


import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    List<UserDto> toDto(List<User> users);

    User toEntity(NewUserRequest request);
}