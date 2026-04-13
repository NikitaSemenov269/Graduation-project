package ru.yandex.category.mapper;

import org.mapstruct.Mapper;
import ru.yandex.category.model.Category;
import ru.yandex.practicum.DTO.category.CategoryDto;
import ru.yandex.practicum.DTO.category.NewCategoryDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    List<CategoryDto> toDto(List<Category> categories);

    Category toEntity(NewCategoryDto newCategoryDto);

    Category toEntity(Long id, CategoryDto categoryDto);
}