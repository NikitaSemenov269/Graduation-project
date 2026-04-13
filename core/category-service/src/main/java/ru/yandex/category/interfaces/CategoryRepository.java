package ru.yandex.category.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.yandex.category.model.Category;


import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "SELECT EXISTS (SELECT 1 FROM events WHERE category_id = :categoryId)", nativeQuery = true)
    boolean existsEventsByCategoryId(@Param("categoryId") Long categoryId);

    List<Category> findAllByIdIn(List<Long> ids);
}