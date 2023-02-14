package com.ssafy.core.repository.article;

import com.ssafy.core.entity.ArticleCluster;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Redis에 클러스터링 저장과 필터링 별 저장 및 조회를 위한 Repository
 *
 * @author 신민철
 */
public interface ArticleClusterRepository extends CrudRepository<ArticleCluster, Long> {
    List<ArticleCluster> findAllByClusterIdAndUserIdOrderByIdDesc(Long clusterId, Long userId);
    List<ArticleCluster> findAllByClusterIdAndUserIdOrderByHeartsDesc(Long clusterId, Long userId);
    List<ArticleCluster> findAllByClusterIdAndUserIdOrderByDistanceAsc(Long clusterId, Long userId);
    void deleteAllByUserId(Long userId);
}
