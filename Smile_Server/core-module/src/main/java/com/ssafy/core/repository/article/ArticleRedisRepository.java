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
    List<ArticleRedis> findAllByClusterIdAndUserIdOrderByIdDesc(Long clusterId, Long userId);
    List<ArticleRedis> findAllByClusterIdAndUserIdOrderByHeartsDesc(Long clusterId, Long userId);
    List<ArticleRedis> findAllByClusterIdAndUserIdOrderByDistanceAsc(Long clusterId, Long userId);
    Optional<ArticleRedis> findByIdAndUserId(Long id, Long userId);
    void deleteAllByUserId(Long userId);
}
