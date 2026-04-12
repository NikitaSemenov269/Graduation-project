package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.practicum.DTO.request.ParticipationRequestDto;
import ru.yandex.practicum.model.ParticipationRequest;


import java.util.List;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    @Mapping(target = "event", source = "event")
    @Mapping(target = "requester", source = "requester")
    ParticipationRequestDto toDto(ParticipationRequest request);

    List<ParticipationRequestDto> toDto(List<ParticipationRequest> requests);
}