package com.ssafy.core.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Data
@RedisHash(value = "articles", timeToLive = 1800)
@Builder
public class ArticleCluster {
    @Id
    private Integer clusterId;

    private String photographerName;

    private Double latitude;

    private Double longitude;

    private Boolean isHeart;

    private Long hearts;

    private String detailAddress;

    private LocalDateTime createdAt;

    private String category;

    private  String photoUrl;
}
