package com.ssafy.api.service;

import com.ssafy.api.dto.ArticleDetailDto;
import com.ssafy.api.dto.ArticleListDto;
import com.ssafy.api.dto.ArticlePostDto;
import com.ssafy.core.entity.Article;
import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.User;
import com.ssafy.core.exception.CustomException;
import com.ssafy.core.exception.ErrorCode;
import com.ssafy.core.repository.ArticleRepository;
import com.ssafy.core.repository.PhotographerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private S3UploaderService s3UploaderService;
    @Autowired
    private PhotographerRepository photographerRepository;

    public void postArticle(String fileName, ArticlePostDto articlePostDto, Photographer photographer){
        Article article = articlePostDto.toEntity();
        article.setPhotoUrls(fileName);
        article.whoPost(photographer);
        articleRepository.save(article);
        System.out.println(article);
    }

    public ArticleDetailDto getArticleDetail(Long id){
        Article article = articleRepository.findById(id).orElseThrow(()->new CustomException(ErrorCode.ARTICLE_NOT_FOUND));

        return new ArticleDetailDto(article);
    }

    public void DeletePost(Long id, User User){
        Article article = articleRepository.findById(id).orElseThrow(()-> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
        if (article.getPhotographer().getId() == User.getId()){
            String photoUriList = article.getPhotoUrls();
            photoUriList.replace("[","").replace("]","");
            List<String> photoUrls = new ArrayList<String>(Arrays.asList(photoUriList.split(",")));
            photoUrls.forEach(new Consumer<String>() {
                @Override
                public void accept(String url) {
                    s3UploaderService.deleteFile(url);
                }
            });

            articleRepository.deleteById(id);
        }
    }

    public ArticleListDto getArticleList(Long id){
        Photographer photographer = photographerRepository.findById(id).orElseThrow(()->new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));
        ArticleListDto articleListDto = new ArticleListDto(photographer);
        return articleListDto;
    }

}
