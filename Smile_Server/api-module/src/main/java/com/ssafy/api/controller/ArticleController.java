package com.ssafy.api.controller;

import com.ssafy.api.dto.ArticleDetailDto;
import com.ssafy.api.dto.ArticleBoardDto;
import com.ssafy.api.dto.ArticlePostDto;
import com.ssafy.api.service.ArticleService;
import com.ssafy.api.service.S3UploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private S3UploaderService s3UploaderService;

    /***
     * 게시글 등록
     * @param articlePostDto
     * @param multipartFile
     * @throws IOException
     */
    @PostMapping()
    public ResponseEntity<HttpStatus> uploadImage(
            @RequestPart("ArticlePostReq") ArticlePostDto articlePostDto,
            @RequestPart("image") List<MultipartFile> multipartFile) throws IOException {
        String fileName = s3UploaderService.upload(multipartFile);
        articleService.postArticle(fileName, articlePostDto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    /***
     * 작가의 아이디로 게시글 조회
     * @param photographerId
     * @return ArticleBoardDto
     */
    @GetMapping("/list/{photographerId}")
    @ResponseBody
    public ResponseEntity<?> getArticleList(@PathVariable("photographerId") Long photographerId){
        ArticleBoardDto articleBoardDto = articleService.getArticleList(photographerId);
        return new ResponseEntity<>(articleBoardDto, HttpStatus.OK);
    }

    /***
     * 게시글 아이디로 게시글 상세조회
     * @param articleId
     * @return ArticleDetailDto
     */
    @GetMapping("/{articleId}")
    @ResponseBody
    public ResponseEntity<ArticleDetailDto> getArticleDetail(@PathVariable("articleId") Long articleId){
        return ResponseEntity.ok(articleService.getArticleDetail(articleId));
    }

    /***
     * 게시글 삭제
     * @param articleId
     * @return delete + articleId
     */
    @DeleteMapping("/{articleId}")
    public ResponseEntity<?> deleteArticle(@PathVariable("articleId") Long articleId){
        articleService.DeletePost(articleId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}