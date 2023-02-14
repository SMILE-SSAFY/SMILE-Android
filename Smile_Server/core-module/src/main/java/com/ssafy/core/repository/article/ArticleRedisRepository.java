package com.ssafy.core.repository.article;

import com.ssafy.core.entity.ArticleRedis;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Redis에 클러스터링 저장과 필터링 별 저장 및 조회를 위한 Repository
 *
 * @author 신민철
 */
public interface ArticleRedisRepository extends CrudRepository<ArticleRedis, Long> {
    List<ArticleRedis> findAllByClusterIdOrderByIdDesc(Long clusterId);
    List<ArticleRedis> findAllByClusterIdOrderByHeartsDesc(Long clusterId);
    List<ArticleRedis> findAllByClusterIdOrderByDistanceAsc(Long clusterId);
    Optional<ArticleRedis> findById(Long id);
}
