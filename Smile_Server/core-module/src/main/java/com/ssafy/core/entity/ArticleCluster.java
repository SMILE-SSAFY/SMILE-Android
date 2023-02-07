package com.ssafy.core.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


/***
 * Redis에 저장할 Entity
 * @author 신민철
 */
@Data
@RedisHash(value = "markers", timeToLive = 1800)
@Builder
public class ArticleCluster {
    @Id
    private Long id;

    private Long userId;
}
