package com.ssafy.api.controller;

import com.ssafy.api.dto.article.ArticleDetailDto;
import com.ssafy.api.dto.article.ArticleBoardDto;
import com.ssafy.api.dto.article.ArticlePostDto;
import com.ssafy.api.dto.article.ArticlePostTestDto;
import com.ssafy.api.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /***
     * 게시글 등록
     * @param articlePostTestDto
     * @throws IOException
     */
    @PostMapping()
    public ResponseEntity<HttpStatus> uploadImage(ArticlePostTestDto articlePostTestDto) throws IOException {
        log.info(articlePostTestDto.toString());
        articleService.postArticle(articlePostTestDto);
        log.info(articlePostTestDto.toString());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /***
     * 작가의 아이디로 게시글 조회
     * @param photographerId
     * @return ArticleBoardDto
     */
    @GetMapping("/photographer/{photographerId}")
    @ResponseBody
    public ResponseEntity<?> getPhotographerInformation(@PathVariable("photographerId") Long photographerId){
        PhotographerInfoDto photographerInfoDto = articleService.getPhotographerInformation(photographerId);
        return new ResponseEntity<>(photographerInfoDto, HttpStatus.OK);
    }

    @GetMapping("/list/{photographerId}")
    @ResponseBody
    public ResponseEntity<?> getArticleList(@PathVariable("photographerId") Long photographerId){
        List<ArticleListDto> articleListDtoList = articleService.getArticleList(photographerId);
        return new ResponseEntity<>(articleListDtoList, HttpStatus.OK);
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
        articleService.deletePost(articleId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /***
     *
     * 게시글 수정
     * @param articleId
     * @param articlePostDto
     * @param multipartFile
     * @return 수정한 게시글 디테일
     * @throws IOException
     *
     *
     */
    @PutMapping("/{articleId}")
    public ResponseEntity<?> updateArticle(
            @PathVariable("articleId") Long articleId,
            @RequestPart("ArticlePostReq") ArticlePostDto articlePostDto,
            @RequestPart("image") List<MultipartFile> multipartFile) throws IOException{

        return ResponseEntity.ok(articleService.updateArticle(articleId, multipartFile, articlePostDto));
    }

}