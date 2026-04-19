package ru.yandex.practicum.event.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.event.EventFullDto;
import ru.yandex.practicum.event.service.EventService;

@RestController
@RequestMapping("/internal/events")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalEventController {

    EventService eventService;

    @GetMapping("/{id}")
    public EventFullDto getEvent(@PathVariable Long id) {
        log.info("GET /internal/events/{} - внутренний вызов", id);
        return eventService.getEventById(id);
    }
}