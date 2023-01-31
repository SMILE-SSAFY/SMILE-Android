package com.ssafy.core.repository.article;

import com.ssafy.core.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 게시글 repository
 *
 * author @신민철
 * author @서재건
 */
public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom {
    List<Article> findByUserId(Long user_id);
    List<Article> findAllByLatitudeBetweenAndLongitudeBetween(Double y1, Double y2, Double x1, Double x2);
}
