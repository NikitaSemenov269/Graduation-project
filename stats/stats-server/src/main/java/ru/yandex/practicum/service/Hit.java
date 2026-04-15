package ru.yandex.practicum.service;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "hits_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id; // тип int согласно swagger, класс-обертка Integer для работы с null.

    @NotBlank(message = "Название сервиса обязательное поле.")
    @Column(name = "app", nullable = false)
    String app;

    @NotBlank(message = "URI не может быть пустым")
    @Column(name = "uri")
    String uri;

    @NotBlank(message = "Ip обязательное поле.")
    @Column(name = "ip", nullable = false)
    String ip;

    @NotNull(message = "Дата и время запроса обязательные поля.")
    @Column(name = "timestamp", nullable = false)
    LocalDateTime timestamp;

}
