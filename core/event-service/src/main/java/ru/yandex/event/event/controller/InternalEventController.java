package ru.yandex.event.event.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.DTO.category.CategoryDto;
import ru.yandex.practicum.DTO.event.EventFullDto;
import ru.yandex.practicum.DTO.user.UserDto;
import ru.yandex.practicum.client.category.CategoryServiceClient;
import ru.yandex.practicum.client.user.UserServiceClient;
import ru.yandex.event.event.interfaces.EventService;

@RestController
@RequestMapping("/internal/events")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalEventController {

    EventService eventService;
    CategoryServiceClient categoryClient;
    UserServiceClient userClient;

    @GetMapping("/{id}")
    public EventFullDto getInternalEvent(@PathVariable Long id) {
        log.info("GET /internal/events/{} - внутренний вызов", id);
        EventFullDto dto = eventService.getEventById(id);

        try {
            CategoryDto category = categoryClient.getCategory(dto.getCategory().getId());
            dto.setCategory(category);
        } catch (Exception e) {
            log.warn("Не удалось загрузить категорию: {}", e.getMessage());
        }

        try {
            UserDto user = userClient.getUser(dto.getInitiator().getId());
            dto.getInitiator().setName(user.getName());
        } catch (Exception e) {
            log.warn("Не удалось загрузить пользователя: {}", e.getMessage());
        }

        return dto;
    }
}