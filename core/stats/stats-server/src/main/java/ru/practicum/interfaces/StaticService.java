package ru.practicum.interfaces;

import ru.practicum.RequestStatisticDto;
import ru.practicum.ResponseStatisticDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StaticService {

    void addHit(RequestStatisticDto requestStatisticDto);

    List<ResponseStatisticDto> findStaticEvent(List<String> uris,
                                                     LocalDateTime start,
                                                     LocalDateTime end,
                                                     Boolean unique);
}
