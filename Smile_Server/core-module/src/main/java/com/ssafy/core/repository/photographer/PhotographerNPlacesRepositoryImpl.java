package com.ssafy.core.repository.photographer;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.core.dto.PhotographerQdslDto;
import com.ssafy.core.entity.Places;
import com.ssafy.core.entity.QPhotographer;
import com.ssafy.core.entity.QPhotographerHeart;
import com.ssafy.core.entity.QPhotographerNPlaces;
import com.ssafy.core.entity.QPlaces;
import com.ssafy.core.entity.QReview;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 사진 작가와 활동 지역 Repository Querydsl Impl
 *
 * @author 서재건
 * @author 김정은
 */
@Slf4j
@RequiredArgsConstructor
public class PhotographerNPlacesRepositoryImpl implements PhotographerNPlacesRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 활동지역에 해당하는 사진작가 조회
     *
     * @param userId
     * @param first
     * @param second
     * @param criteria
     * @return List<PhotographerQuerydslDto>
     */
    @Override
    public List<PhotographerQdslDto> findPhotographerByAddress(Long userId, String first, String second, String criteria) {
        QPhotographerNPlaces photographerNPlaces = QPhotographerNPlaces.photographerNPlaces;
        QPlaces places = QPlaces.places;
        QPhotographer photographer = QPhotographer.photographer;
        QPhotographerHeart photographerHeart = QPhotographerHeart.photographerHeart;
        QReview review = QReview.review;

        // TODO : 시/도 문자열 갯수 체크 최적화
        BooleanBuilder builder = new BooleanBuilder();
        if (first.length() == 2) {
            char[] chars = first.toCharArray();
            String siDo = chars[0] + "%" + chars[1] + "%";
            builder.and(places.first.like(siDo));
        } else {
            builder.and(places.first.eq(first));
        }

        return jpaQueryFactory
                .select(Projections.constructor(PhotographerQdslDto.class,
                        photographer,
                        review.score.avg().as("score"),
                        photographerHeart.id.count(),
                        review.id.count(),
                        new CaseBuilder()
                                .when(photographer.id.in(
                                        JPAExpressions
                                                .select(photographerHeart.photographer.id)
                                                .from(photographerHeart)
                                                .where(photographerHeart.user.id.eq(userId))
                                )).then(true)
                                .otherwise(false)
                ))
                .from(photographer)
                .leftJoin(photographerHeart).on(photographer.eq(photographerHeart.photographer))
                .leftJoin(review).on(photographer.eq(review.photographer))
                .join(photographerNPlaces).on(photographer.eq(photographerNPlaces.photographer))
                .join(places).on(places.eq(photographerNPlaces.places))
                .where(builder, places.second.eq(second))
                .groupBy(photographer.id)
                .orderBy(findCriteria(criteria, photographerHeart.id.count(), review.score.avg(), photographer.id, review.id.count()))
                .fetch();
    }

    /**
     * querydsl order by 에 사용되는 method
     *
     * @param criteria
     * @param hearts
     * @param score
     * @param id
     * @param reviews
     * @return OrderSpecifier
     */
    private OrderSpecifier<?> findCriteria(String criteria, NumberExpression<Long> hearts, NumberExpression<Double> score, NumberPath<Long> id, NumberExpression<Long> reviews) {

        switch (criteria) {
            case "heart":
                return hearts.desc();
            case "score":
                return score.desc();
            case "review":
                return reviews.desc();
            default:
                return id.desc();
        }
    }

    /**
     * 사진작가 별 활동지역 조회
     *
     * @param photographerId
     * @return Places
     */
    @Override
    public List<Places> findPlacesByPhotographer(Long photographerId) {
        QPhotographerNPlaces photographerNPlaces = QPhotographerNPlaces.photographerNPlaces;
        QPlaces places = QPlaces.places;

        return jpaQueryFactory
                .selectFrom(places)
                .join(photographerNPlaces).on(photographerNPlaces.places.eq(places))
                .where(photographerNPlaces.photographer.id.eq(photographerId))
                .fetch();
    }
}
