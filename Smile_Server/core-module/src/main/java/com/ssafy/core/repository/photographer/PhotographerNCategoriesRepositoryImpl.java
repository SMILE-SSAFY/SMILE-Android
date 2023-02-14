package com.ssafy.core.repository.photographer;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.core.dto.CategoriesQdslDto;
import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.QCategories;
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
     * categoryId로 사진 작가 조회
     *
     * @param categoryIdList
     * @return List<PhotographerQuerydslDto>
     */
    @Override
    public List<Photographer> findByCategoryId(List<Long> categoryIdList) {
        QPhotographerNCategories photographerNCategories = QPhotographerNCategories.photographerNCategories;

        return jpaQueryFactory
                .selectDistinct(photographerNCategories.photographer)
                .from(photographerNCategories)
                .where(photographerNCategories.category.id.in(categoryIdList))
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
