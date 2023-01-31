package com.ssafy.core.entity;

import com.ssafy.core.dto.ArticleSearchDto;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.List;

/***
 * @author 신민철
 * Redis에 저장할 Entity
 */
@Data
@RedisHash(value = "articles", timeToLive = 1800)
@Builder
public class ArticleCluster {
    @Id
    private Long Id;

    private List<ArticleSearchDto> articleSearchDtoList;

}
