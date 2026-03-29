package ru.practicum.DTO.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.enums.ParticipationStatus;


import java.util.List;

/**
 * Изменение статуса запроса на участие в событии текущего пользователя
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestStatusUpdateRequest {

    /**
     * Идентификаторы запросов на участие в событии текущего пользователя
     */
    @NotEmpty(message = "Список запросов не может быть пустым")
    private List<Long> requestIds;

    /**
     * Новый статус запроса на участие в событии (CONFIRMED, REJECTED)
     */
    @NotNull(message = "Статус не может быть пустым")
    private ParticipationStatus status;
}