package ru.yandex.practicum.interfaces;

import ru.yandex.practicum.DTO.stats.EndpointHitDto;
import ru.yandex.practicum.DTO.stats.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    void hit(EndpointHitDto dto);
    List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
