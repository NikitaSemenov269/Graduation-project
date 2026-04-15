package ru.yandex.practicum;

import ru.yandex.practicum.DTO.stats.EndpointHitDto;
import ru.yandex.practicum.DTO.stats.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsClient {
    void saveHit(EndpointHitDto endpointHitDto);
    List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}