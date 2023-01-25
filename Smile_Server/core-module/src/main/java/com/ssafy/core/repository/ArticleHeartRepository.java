package com.ssafy.core.repository;

import com.ssafy.core.entity.Article;
import com.ssafy.core.entity.ArticleHeart;
import com.ssafy.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleHeartRepository extends JpaRepository<ArticleHeart, Long> {
    Optional<ArticleHeart> findByUserAndArticle(User user, Article article);
    Long countByArticle(Article article);
}
