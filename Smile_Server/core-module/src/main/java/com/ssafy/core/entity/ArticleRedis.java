package com.ssafy.core.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

/***
 * @author 신민철
 * redis에 cache로 article 정보를 저장
 */
@Data
@RedisHash(value = "article", timeToLive = 1800)
@Builder
public class ArticleRedis {
    @Id
    private Long id;
    @Indexed
    private Long clusterId;

    private Long photographerId;

    private String photographerName;

    private Double latitude;

    private Double longitude;

    private Double distance;

    private Boolean isHeart;

    private Long hearts;

    private String detailAddress;

    private LocalDateTime createdAt;

    private String category;

    private String photoUrl;

}
