package com.ssafy.core.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.core.dto.CategoriesQdslDto;
import com.ssafy.core.dto.PhotographerQuerydslDto;
import com.ssafy.core.entity.QCategories;
import com.ssafy.core.entity.QPhotographer;
import com.ssafy.core.entity.QPhotographerHeart;
import com.ssafy.core.entity.QPhotographerNCategories;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * querydsl 작성하는 클래스
 *
 * @author 서재건
 * @author 김정은
 */
@Slf4j
@RequiredArgsConstructor
public class PhotographerNCategoriesRepositoryImpl implements PhotographerNCategoriesRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * categoryId로 사진 작가 및 사진 작가의 좋아요 상태 조회
     *
     * @param userId
     * @param categoryIdList
     * @return List<PhotographerQuerydslDto>
     */
    @Override
    public List<PhotographerQuerydslDto> findByCategoryId(Long userId, List<Long> categoryIdList) {
        QPhotographerNCategories photographerNCategories = QPhotographerNCategories.photographerNCategories;
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
                .join(photographerNCategories).on(photographer.eq(photographerNCategories.photographer))
                .where(photographerNCategories.category.id.in(categoryIdList))
                .groupBy(photographer.id)
                .fetch();
    }

    /**
     * 사진작가 별 카테고리 검색
     *
     * @param photographerId
     * @return 검색된 카테고리 결과(id, name, price, description)
     */
    @Override
    public List<CategoriesQdslDto> findCategoriesByPhotographerId(Long photographerId){
        QPhotographerNCategories photographerNCategories = QPhotographerNCategories.photographerNCategories;
        QCategories categories = QCategories.categories;

        return jpaQueryFactory
                .select(Projections.constructor(CategoriesQdslDto.class, categories.id, categories.name, photographerNCategories.price, photographerNCategories.description))
                .from(categories)
                .join(photographerNCategories).on(categories.eq(photographerNCategories.category))
                .where(photographerNCategories.photographer.id.eq(photographerId))
                .fetch();
    }
}
