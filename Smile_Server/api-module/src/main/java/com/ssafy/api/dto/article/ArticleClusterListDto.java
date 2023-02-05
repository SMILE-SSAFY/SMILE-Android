package com.ssafy.api.dto.article;

import com.ssafy.core.dto.ArticleSearchDto;
import com.ssafy.core.entity.ArticleRedis;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/***
 * 클러스터링시 마커 클릭시 마커에 포함된 게시글 반환
 */
@Builder
@Data
public class ArticleClusterListDto {
    private Boolean isEndPage;
    private List<ArticleRedis> articleRedisList;
}
