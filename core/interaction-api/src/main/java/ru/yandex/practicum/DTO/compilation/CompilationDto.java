package ru.yandex.practicum.DTO.compilation;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.DTO.event.EventShortDto;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationDto {

    Long id;

    List<EventShortDto> events;

    Boolean pinned;

    String title;
}