package com.ssafy.core.repository;

import com.ssafy.core.dto.PhotographerQdslDto;

import java.util.List;

/**
 * 사진 작가와 활동 지역 Repository Querydsl custom
 *
 * author @서재건
 */
public interface PhotographerNPlacesRepositoryCustom {

    /**
     * 활동지역에 해당하는 사진작가 조회
     *
     * @param userId
     * @param first
     * @param second
     * @return List<PhotographerQuerydslDto>
     */
    List<PhotographerQdslDto> findPhotographerByAddress(Long userId, String first, String second);
}
