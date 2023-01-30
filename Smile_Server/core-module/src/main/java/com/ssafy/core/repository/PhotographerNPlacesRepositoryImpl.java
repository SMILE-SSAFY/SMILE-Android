package com.ssafy.core.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.core.dto.PhotographerQuerydslDto;
import com.ssafy.core.entity.Places;
import com.ssafy.core.entity.QPhotographer;
import com.ssafy.core.entity.QPhotographerHeart;
import com.ssafy.core.entity.QPhotographerNPlaces;
import com.ssafy.core.entity.QPlaces;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 사진 작가와 활동 지역 Repository Querydsl Impl
 *
 * @author 서재건
 * @author 김정은
 */
@RequiredArgsConstructor
public class PhotographerNPlacesRepositoryImpl implements PhotographerNPlacesRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 활동지역에 해당하는 사진작가 조회
     *
     * @param userId
     * @param first
     * @param second
     * @return List<PhotographerQuerydslDto>
     */
    @Override
    public List<PhotographerQuerydslDto> findPhotographerByAddress(Long userId, String first, String second) {
        QPhotographerNPlaces photographerNPlaces = QPhotographerNPlaces.photographerNPlaces;
        QPlaces places = QPlaces.places;
        QPhotographer photographer = QPhotographer.photographer;
        QPhotographerHeart photographerHeart = QPhotographerHeart.photographerHeart;

        return jpaQueryFactory
                .select(Projections.constructor(PhotographerQuerydslDto.class,
                        photographer,
                        photographerHeart.id.count(),
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
                .join(photographerNPlaces).on(photographer.eq(photographerNPlaces.photographer))
                .join(places).on(places.eq(photographerNPlaces.places))
                .where(places.first.eq(first), places.second.eq(second))
                .groupBy(photographer.id)
                .fetch();
    }

    /**
     * 사진작가 별 활동지역 조회
     *
     * @param photographerId
     * @return Places
     */
    @Override
    public List<Places> findPlacesByPhotographer(Long photographerId){
        QPhotographerNPlaces photographerNPlaces = QPhotographerNPlaces.photographerNPlaces;
        QPlaces places = QPlaces.places;

        return jpaQueryFactory
                .selectFrom(places)
                .join(photographerNPlaces).on(photographerNPlaces.places.eq(places))
                .where(photographerNPlaces.photographer.id.eq(photographerId))
                .fetch();
    }
}
