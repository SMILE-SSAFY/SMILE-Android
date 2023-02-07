package com.ssafy.core.repository.article;

import com.ssafy.core.entity.ArticleCluster;
import com.ssafy.core.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/***
 * 클러스터링을 위한 repo
 * redis에 저장
 *
 * @author 신민철
 */
public interface ArticleClusterRepository extends CrudRepository<ArticleCluster, Long> {
    Optional<ArticleCluster> findById(Long Id);
    Optional<ArticleCluster> findByUserId(Long Id);
    void deleteByUser(User user);
}
