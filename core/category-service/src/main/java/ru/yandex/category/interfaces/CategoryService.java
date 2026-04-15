package ru.yandex.category.interfaces;

import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.DTO.category.CategoryDto;
import ru.yandex.practicum.DTO.category.NewCategoryDto;
import java.util.List;
import java.util.Map;

public interface CategoryService {

    CategoryDto addCategory(NewCategoryDto newCategoryDto);

    CategoryDto updateCategory(Long catId, CategoryDto categoryDto);

    void deleteCategory(Long catId);

    List<CategoryDto> getCategories(Pageable pageable);

    CategoryDto getCategory(Long catId);

    Map<Long, CategoryDto> getCategoriesBatch(List<Long> ids);
}
