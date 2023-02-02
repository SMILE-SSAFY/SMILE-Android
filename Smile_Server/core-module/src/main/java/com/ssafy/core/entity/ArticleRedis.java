package com.ssafy.core.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@Data
@RedisHash(value = "article")
@Builder
public class ArticleRedis {
    @Id
    private Long id;
    @Indexed
    private Long clusterId;

    private String photographerName;

    private Double latitude;

    private Double longitude;

    private Boolean isHeart;

    private Long hearts;

    private String detailAddress;

    private LocalDateTime createdAt;

    private String category;

    private String photoUrl;

}
