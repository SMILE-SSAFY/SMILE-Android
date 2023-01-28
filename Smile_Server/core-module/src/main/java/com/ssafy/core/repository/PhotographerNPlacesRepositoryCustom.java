package com.ssafy.core.repository;

import com.ssafy.core.dto.PhotographerQuerydslDto;
import com.ssafy.core.entity.Photographer;

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
     * @param first
     * @param second
     * @return List<Photographer>
     */
    List<PhotographerQuerydslDto> findPhotographerByAddress(Long userId, String first, String second);
}
