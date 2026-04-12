package ru.yandex.practicum.compilation.interfaces;

import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.DTO.compilation.CompilationDto;
import ru.yandex.practicum.DTO.compilation.NewCompilationDto;
import ru.yandex.practicum.DTO.compilation.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {

    CompilationDto createCompilation(NewCompilationDto dto);

    void deleteCompilation(Long compId);

    CompilationDto updateCompilation(Long compId, UpdateCompilationRequest request);

    List<CompilationDto> getCompilations(Boolean pinned, Pageable pageable);

    CompilationDto getCompilation(Long compId);
}
