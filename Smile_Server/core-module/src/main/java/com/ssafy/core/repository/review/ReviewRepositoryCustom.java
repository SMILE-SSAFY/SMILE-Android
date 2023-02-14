package com.ssafy.core.repository.review;

import com.ssafy.core.dto.ReviewQdslDto;

/**
 * querydsl 인터페이스
 * 
 * @author 서재건
 */
public interface ReviewRepositoryCustom {
    ReviewQdslDto findByPhotographerId(Long photographerId);
}
