package com.ssafy.core.repository.article;

import com.ssafy.core.dto.ArticleQdslDto;

import java.util.List;

/**
 * querydsl 작성 관련 인터페이스
 *
 * author @서재건
 */
public interface ArticleRepositoryCustom {

    /**
     * 카테고리에 해당하는 게시글 조회
     *
     * @param userId
     * @param categoryNameList
     * @return List<ArticleQuerydslDto>
     */
    List<ArticleQdslDto> findByCategoryName(Long userId, List<String> categoryNameList);
}
