package ru.yandex.request.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.request.interfaces.RequestService;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalRequestController {

    RequestService requestService;

    @GetMapping("/events/{eventId}/confirmed-requests")
    public Long getConfirmedRequests(@PathVariable Long eventId) {
        log.info("GET внутренний запрос количества подтверждённых заявок для события {}", eventId);
        return requestService.getConfirmedRequests(eventId);
    }

    @PostMapping("/events/confirmed-requests/batch")
    public Map<Long, Long> getConfirmedRequestsBatch(@RequestBody List<Long> eventIds) {
        log.info("POST внутренний пакетный запрос для {} событий", eventIds.size());
        return requestService.getConfirmedRequestsBatch(eventIds);
    }
}
