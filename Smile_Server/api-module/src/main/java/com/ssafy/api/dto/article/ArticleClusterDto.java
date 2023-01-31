package com.ssafy.api.dto.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ArticleClusterDto {
    private Integer clusterId;

    private Integer numOfCluster;

    private Double centroidLat;

    private Double centroidLong;
}
