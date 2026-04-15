package ru.yandex.practicum.DTO.location;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Широта и долгота места проведения события
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationDto {

    /**
     * Широта
     */
    Float lat;

    /**
     * Долгота
     */
    Float lon;
}