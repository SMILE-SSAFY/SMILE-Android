package com.ssafy.api.controller;

import com.ssafy.api.dto.ArticleDetailDto;
import com.ssafy.api.dto.ArticleListDto;
import com.ssafy.api.dto.ArticlePostDto;
import com.ssafy.api.service.ArticleService;
import com.ssafy.api.service.S3UploaderService;
import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("")
@Controller
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private S3UploaderService s3UploaderService;

    @PostMapping("/api/article")
    public void uploadImage(@RequestPart(value="ArticlePostReq") ArticlePostDto articlePostDto,
                            @RequestPart(value="image") List<MultipartFile> multipartFile,
                            Photographer photographer) throws IOException {
        String fileName = s3UploaderService.upload(multipartFile);
        articleService.postArticle(fileName, articlePostDto, photographer);
        System.out.println(fileName);
    }

    @GetMapping("/api/article/list/{photographer-id}")
    @ResponseBody
    public ArticleListDto getArticleList(@PathVariable("photographer-id") Long photographerId){
        return articleService.getArticleList(photographerId);
    }

    @GetMapping("/api/article/{article-id}")
    @ResponseBody
    public ArticleDetailDto getArticleDetail(@PathVariable("article-id") Long articleId){
        return articleService.getArticleDetail(articleId);
    }

    @DeleteMapping("/api/article/{article-id}")
    public void deleteArticle(@PathVariable("article-id") Long articleId,
                                        User user){
        articleService.DeletePost(articleId, user);
    }

}