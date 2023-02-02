package com.ssafy.core.repository.article;

import com.ssafy.core.entity.ArticleRedis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleRedisRepository extends CrudRepository<ArticleRedis, Long> {
    Page<ArticleRedis> findByClusterId(Long clusterId, Pageable pageable);
}
