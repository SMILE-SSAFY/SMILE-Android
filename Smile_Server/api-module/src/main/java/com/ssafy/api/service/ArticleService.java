package com.ssafy.api.service;

import com.ssafy.api.dto.ArticlePostDto;
import com.ssafy.core.entity.Article;
import com.ssafy.core.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public Long postArticle(String fileName, ArticlePostDto articlePostDto){
        Article article = articlePostDto.toEntity();
        article.setPhotoUrls(fileName);
        articleRepository.save(article);
        System.out.println(article);
        return article.getId();
    }

}
