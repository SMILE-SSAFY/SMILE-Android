package com.ssafy.core.repository;

import com.ssafy.core.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 카테고리 관련 Repository
 *
 * author @김정은
 * author @서재건
 */
public interface CategoriesRepository extends JpaRepository<Categories, Long> {

    @Query("SELECT id from Categories where name like %:name%")
    List<Long> findAllIdByNameContaining(String name);
}
