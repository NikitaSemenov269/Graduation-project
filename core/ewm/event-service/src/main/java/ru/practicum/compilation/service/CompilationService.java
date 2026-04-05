package ru.practicum.compilation.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.interfaces.CompilationRepository;
import ru.practicum.interfaces.EventRepository;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;
import ru.practicum.model.QCompilation;
import ru.practicum.model.QEvent;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CompilationService {
    private final CompilationRepository compRep;
    private final EventRepository eventRep;
    private final CompilationMapper mapper;
    private final EventMapper eventMapper;
    private final JPAQueryFactory queryFactory;

    /**
     * Получение списка подборок с возможностью фильтрации по статусу закрепления
     */
    @Transactional(readOnly = true)
    public List<CompilationDto> findCompilations(Boolean pinned, Pageable pageable) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (pinned == null) pinned = false;
        predicate.and(QCompilation.compilation.pinned.eq(pinned));

        List<Long> compilationIds = queryFactory
                .select(QCompilation.compilation.id)
                .from(QCompilation.compilation)
                .where(predicate)
                .orderBy(QCompilation.compilation.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        if (compilationIds.isEmpty()) {
            log.info("Нет подборок с статусом закрепления ={}", pinned);
            return Collections.emptyList();
        }

        List<CompilationDto> compilationDtos = queryFactory
                .selectFrom(QCompilation.compilation)
                .leftJoin(QCompilation.compilation.events, QEvent.event).fetchJoin()
                .where(QCompilation.compilation.id.in(compilationIds))
                .orderBy(QCompilation.compilation.id.asc())
                .fetch()
                .stream()
                .map(comp -> {
                    CompilationDto dto = mapper.toDto(comp);
                    dto.setEvents(comp.getEvents().stream()
                            .map(eventMapper::toShortDto)
                            .collect(Collectors.toList()));
                    return dto;
                })
                .collect(Collectors.toList());

        log.info("Получен список подборок событий размером: {}", compilationDtos.size());
        return compilationDtos;
    }

    /**
     * Поиск подборки по ID
     */
    @Transactional(readOnly = true)
    public CompilationDto findCompilationById(Long compId) {

        Compilation compilation = compRep.findById(compId).orElseThrow(
                () -> new NotFoundException("Подборка с ID: " + compId + " не найдена."));

        List<EventShortDto> eventDtos = compilation.getEvents().stream()
                .map(eventMapper::toShortDto)
                .toList();

        CompilationDto compilationDto = mapper.toDto(compilation);
        compilationDto.setEvents(eventDtos);

        log.info("Найдена подборка с ID: {}", compId);
        return compilationDto;
    }


    /**
     * Сохранение подборки
     */
    public CompilationDto saveCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = mapper.toEntity(newCompilationDto);
        Set<Event> events = new HashSet<>();
        if (!ObjectUtils.isEmpty(newCompilationDto.getEvents())) {
            events = new HashSet<>(queryFactory.selectFrom(QEvent.event)
                    .where(QEvent.event.id.in(newCompilationDto.getEvents()))
                    .fetch());
        }

        compilation.setEvents(events);
        try {
            Compilation savedCompilation = compRep.save(compilation);
            CompilationDto compilationDto = mapper.toDto(savedCompilation);

            List<EventShortDto> eventShortDtos = savedCompilation.getEvents().stream()
                    .map(eventMapper::toShortDto)
                    .collect(Collectors.toList());

            compilationDto.setEvents(eventShortDtos);
            log.info("Сохранена новая подборка с ID: {}", savedCompilation.getId());
            return compilationDto;
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Ошибка при сохранении новой подборки.");
        }
    }

    /**
     * Удаление подборки по ID
     */
    public void deleteCompilation(Long compId) {
        try {
            compRep.deleteById(compId);
            log.info("Подборка с ID: {} удалена.", compId);
        } catch (Exception e) {
            throw new RuntimeException("");
        }
    }

    /**
     * Обновление подборки по ID
     */
    public CompilationDto updateCompilation(Long id, UpdateCompilationRequest updReqCompDto) {
        Compilation compilation = compRep.findById(id)
                .orElseThrow(() -> new NotFoundException("Подборка с ID: " + id + " не найдена."));

        if (updReqCompDto.getPinned() != null && !compilation.getPinned().equals(updReqCompDto.getPinned())) {
            compilation.setPinned(updReqCompDto.getPinned());
        }
        if (updReqCompDto.getTitle() != null && !compilation.getTitle().equals(updReqCompDto.getTitle())) {
            compilation.setTitle(updReqCompDto.getTitle());
        }

        List<EventShortDto> eventDtos = null;

        if (updReqCompDto.getEvents() != null) {
            if (!updReqCompDto.getEvents().isEmpty()) {
                Set<Long> eventsId = compilation.getEvents().stream()
                        .map(Event::getId)
                        .collect(Collectors.toSet());

                boolean allPresent = new HashSet<>(eventsId).containsAll(updReqCompDto.getEvents());

                if (!allPresent) {
                    Set<Event> events = new HashSet<>(eventRep.findAllById(updReqCompDto.getEvents()));
                    compilation.setEvents(events);
                    eventDtos = events.stream()
                            .map(eventMapper::toShortDto)
                            .collect(Collectors.toList());
                }
            } else {
                compilation.setEvents(Collections.emptySet());
            }
        }
        try {
            compRep.save(compilation);
            CompilationDto compilationDto = mapper.toDto(compilation);
            if (eventDtos != null) compilationDto.setEvents(eventDtos);
            log.info("Подборка с ID: {} успешно обновлена.", id);
            return compilationDto;
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Ошибка при сохранении обновленной категории с ID: " + id);
        }
    }
}
