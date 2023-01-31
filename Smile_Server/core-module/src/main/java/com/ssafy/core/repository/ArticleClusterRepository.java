package com.ssafy.core.repository;

import com.ssafy.core.entity.Article;
import com.ssafy.core.entity.ArticleCluster;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ArticleClusterRepository extends CrudRepository<ArticleCluster, Long> {
    Optional<ArticleCluster> findById(Long Id);
}
