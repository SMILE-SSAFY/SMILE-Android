package com.ssafy.core.repository;

import com.ssafy.core.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByPhotographerId(Long id);
}
