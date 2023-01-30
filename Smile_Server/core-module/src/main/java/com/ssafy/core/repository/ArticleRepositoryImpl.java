package com.ssafy.core.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.core.dto.ArticleQdslDto;
import com.ssafy.core.entity.QArticle;
import com.ssafy.core.entity.QArticleHeart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * querydsl 작성 관련 클래스
 *
 * author @서재건
 */
@Slf4j
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 카테고리에 해당하는 게시글 조회
     *
     * @param userId
     * @param categoryNameList
     * @return List<ArticleQuerydslDto>
     */
    @Override
    public List<ArticleQdslDto> findByCategoryName(Long userId, List<String> categoryNameList) {
        QArticle article = QArticle.article;
        QArticleHeart articleHeart = QArticleHeart.articleHeart;

        return jpaQueryFactory
                .select(Projections.constructor(ArticleQdslDto.class,
                        article,
                        articleHeart.id.count(),
                        new CaseBuilder()
                                .when(article.id.in(
                                        JPAExpressions
                                                .select(articleHeart.article.id)
                                                .from(articleHeart)
                                                .where(articleHeart.user.id.eq(userId))
                                )).then(true)
                                .otherwise(false)
                ))
                .from(article)
                .leftJoin(articleHeart).on(article.eq(articleHeart.article))
                .where(article.category.in(categoryNameList))
                .groupBy(article.id)
                .fetch();
    }
}
