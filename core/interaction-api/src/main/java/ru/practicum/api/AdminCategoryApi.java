package ru.practicum.api;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.practicum.DTO.category.CategoryDto;
import ru.practicum.DTO.category.NewCategoryDto;

@FeignClient(name = "category_admin", path = "/admin/categories")
public interface AdminCategoryApi {
    @PostMapping
    CategoryDto addCategory(@Valid @RequestBody NewCategoryDto newCategoryDto);

    @DeleteMapping("/{catId}")
    void deleteCategory(@PathVariable Long catId);

    @PatchMapping("/{catId}")
    CategoryDto updateCategory(@PathVariable Long catId,
                               @Valid @RequestBody CategoryDto categoryDto);

}
