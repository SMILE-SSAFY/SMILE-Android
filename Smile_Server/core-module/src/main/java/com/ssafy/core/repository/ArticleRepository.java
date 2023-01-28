package com.ssafy.core.repository;

import com.ssafy.core.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByUserId(Long user_id);
    List<Article> findAllByLatitudeBetweenAndLongitudeBetween(Double y1, Double y2, Double x1, Double x2);
}
