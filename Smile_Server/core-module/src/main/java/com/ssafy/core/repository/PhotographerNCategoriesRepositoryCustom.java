package com.ssafy.core.repository;

import com.ssafy.core.entity.Photographer;

import java.util.List;

/**
 * querydsl 작성할 인터페이스
 *
 * author @서재건
 */
public interface PhotographerNCategoriesRepositoryCustom {

    /**
     * categoryId로 사진 작가 조회
     *
     * @param categoryId
     * @return List<Photographer>
     */
    List<Photographer> findByCategoryId(Long categoryId);
}
