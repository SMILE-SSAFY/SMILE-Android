package com.ssafy.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


/**
 * 게시글 좋아요 Entity
 *
 * @author 신민철
 * @author 서재건
 */
@Entity
@NoArgsConstructor
@Getter
public class ArticleHeart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    public ArticleHeart(User user, Article article){
        this.user = user;
        this.article = article;
    }
}
