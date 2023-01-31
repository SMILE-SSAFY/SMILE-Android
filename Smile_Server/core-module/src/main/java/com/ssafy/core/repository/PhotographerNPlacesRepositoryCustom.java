package com.ssafy.core.repository;

import com.ssafy.core.dto.PhotographerQdslDto;
import com.ssafy.core.entity.Places;

import java.util.List;

/**
 * 사진 작가와 활동 지역 Repository Querydsl custom
 *
 * @author 서재건
 * @author 김정은
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

    /**
     * 사진작가 별 활동지역 검색
     *
     * @param photographerId
     * @return 활동지역
     */
    List<Places> findPlacesByPhotographer(Long photographerId);
}
