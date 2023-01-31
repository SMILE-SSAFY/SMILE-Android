package com.ssafy.core.repository;

import com.ssafy.core.entity.Article;
import com.ssafy.core.entity.ArticleHeart;
import com.ssafy.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/***
 * 게시글 좋아요를 위한 Repo
 * @author 신민철
 */
public interface ArticleHeartRepository extends JpaRepository<ArticleHeart, Long> {
    Optional<ArticleHeart> findByUserAndArticle(User user, Article article);
    void deleteByUserAndArticle(User user, Article article);
    Long countByArticle(Article article);
}
