package ru.practicum.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.DTO.category.CategoryDto;

import java.util.List;

@FeignClient(name = "category_public", path = "/categories")
public interface PublicCategoryApi {

    @GetMapping
    List<CategoryDto> getCategories(@RequestParam(defaultValue = "0") Integer from,
                                    @RequestParam(defaultValue = "10") Integer size);

    @GetMapping("/{catId}")
    CategoryDto getCategoryById(@PathVariable Long catId);
}
