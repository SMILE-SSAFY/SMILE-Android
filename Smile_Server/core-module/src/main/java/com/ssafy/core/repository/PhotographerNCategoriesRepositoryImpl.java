package com.ssafy.core.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.QPhotographerNCategories;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * querydsl 작성하는 클래스
 *
 * author @서재건
 */
@Slf4j
@RequiredArgsConstructor
public class PhotographerNCategoriesRepositoryImpl implements PhotographerNCategoriesRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * categoryId로 사진 작가 조회
     *
     * @param categoryId
     * @return List<Photographer>
     */
    @Override
    public List<Photographer> findByCategoryId(Long categoryId) {
        QPhotographerNCategories photographerNCategories = QPhotographerNCategories.photographerNCategories;

        return jpaQueryFactory
                .select(photographerNCategories.photographer)
                .from(photographerNCategories)
                .where(photographerNCategories.category.id.eq(categoryId))
                .fetch();
    }
}
