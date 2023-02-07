package com.ssafy.api.dto.article;

import lombok.Builder;
import lombok.Data;

/***
 * 클러스터링시 포함된 게시글의 개수, 중심좌표 반환
 *
 * @author 신민철
 */
@Builder
@Data
public class ArticleClusterDto {
    private Long clusterId;

    private Integer numOfCluster;

    private Double centroidLat;

    private Double centroidLng;
}
