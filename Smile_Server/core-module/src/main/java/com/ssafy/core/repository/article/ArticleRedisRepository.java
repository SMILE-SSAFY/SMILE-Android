package com.ssafy.core.repository.article;

import com.ssafy.core.entity.ArticleRedis;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleRedisRepository extends CrudRepository<ArticleRedis, Long> {
    List<ArticleRedis> findAllByClusterIdOrderByIdDesc(Long clusterId);
    List<ArticleRedis> findAllByClusterIdOrderByHeartsDesc(Long clusterId);
    List<ArticleRedis> findAllByClusterIdOrderByDistanceAsc(Long clusterId);
}
