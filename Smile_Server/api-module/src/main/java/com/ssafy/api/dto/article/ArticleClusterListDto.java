package com.ssafy.api.dto.article;

import com.ssafy.core.entity.ArticleCluster;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/***
 * 클러스터링시 마커 클릭시 마커에 포함된 게시글 반환
 *
 * @author 신민철
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleClusterListDto {
    private Boolean isEndPage;
    private List<ArticleCluster> articleClusterList;
}
