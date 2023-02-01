package com.ssafy.api.dto.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/***
 * 클러스터링시 포함된 게시글의 개수, 중심좌표 반환
 */
@Builder
@Data
@AllArgsConstructor
public class ArticleClusterDto {
    private Long clusterId;

    private Integer numOfCluster;

    private Double centroidLat;

    private Double centroidLng;
}
