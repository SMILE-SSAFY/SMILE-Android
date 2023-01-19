package com.ssafy.core.repository;

import com.ssafy.core.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query(value = "SELECT * FROM board WHERE user_id = :userId ORDER BY id DESC", nativeQuery = true)
    List<Article> findByUserId(Long userId);
}
