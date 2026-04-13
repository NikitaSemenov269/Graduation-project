package ru.yandex.event.event.controller;

import lombok.RequiredArgsConstructor;
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
public class InternalEventController {

    private final EventService eventService;
    private final CategoryServiceClient categoryClient;
    private final UserServiceClient userClient;

    @GetMapping("/{id}")
    public EventFullDto getInternalEvent(@PathVariable Long id) {
        log.info("GET /internal/events/{} - внутренний вызов", id);
        EventFullDto dto = eventService.getEventById(id);

        // Заполните category и initiator через клиенты
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