package com.ssafy.core.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.core.dto.ReviewQdslDto;
import com.ssafy.core.entity.QReview;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * querydsl 클래스
 * 
 * @author 서재건
 */
@Slf4j
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public ReviewQdslDto findByPhotographerId(Long photographerId) {
        QReview review = QReview.review;

        return jpaQueryFactory
                .select(Projections.constructor(ReviewQdslDto.class,
                        review.score.avg(),
                        review.id.count()
                ))
                .from(review)
                .where(review.photographer.id.eq(photographerId)).fetchOne()
                ;
    }
}
