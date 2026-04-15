package ru.yandex.user.interfaces;

import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.DTO.user.NewUserRequest;
import ru.yandex.practicum.DTO.user.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(NewUserRequest request);

    List<UserDto> getUsers(List<Long> ids, Pageable pageable);

    void deleteUser(Long userId);

    UserDto getUser(Long userId);
}
