package com.ssafy.api.dto.article;

import com.ssafy.core.dto.ArticleSearchDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/***
 * 클러스터링시 마커 클릭시 마커에 포함된 게시글 반환
 */
@Builder
@Data
public class ArticleClusterListDto {
    private Long clusterId;
    private List<ArticleSearchDto> articleSearchDtoList;
}
