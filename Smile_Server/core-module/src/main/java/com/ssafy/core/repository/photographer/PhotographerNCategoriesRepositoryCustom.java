package com.ssafy.core.repository.photographer;

import com.ssafy.core.dto.CategoriesQdslDto;
import com.ssafy.core.dto.PhotographerQdslDto;

import java.util.List;

/**
 * querydsl 작성할 인터페이스
 *
 * @author 서재건
 */
public interface PhotographerNCategoriesRepositoryCustom {

    /**
     * categoryId로 사진 작가 및 사진 작가의 좋아요 상태 조회
     *
     * @param userId
     * @param categoryIdList
     * @return List<PhotographerQuerydslDto>
     */
    List<PhotographerQdslDto> findByCategoryId(Long userId, List<Long> categoryIdList);

    /**
     * photographerId로 카테고리 조회
     *
     * @param photographerId
     * @return List<CategoriesQdslDto>
     */
    List<CategoriesQdslDto> findCategoriesByPhotographerId(Long photographerId);
}
