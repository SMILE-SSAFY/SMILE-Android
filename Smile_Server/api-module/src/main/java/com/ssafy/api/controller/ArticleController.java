package com.ssafy.api.controller;

import com.ssafy.api.dto.article.ArticleDetailDto;
import com.ssafy.api.dto.article.ArticleHeartDto;
import com.ssafy.api.dto.article.ArticleListDto;
import com.ssafy.api.dto.article.ArticlePostDto;
import com.ssafy.api.dto.article.ArticleSearchDto;
import com.ssafy.api.dto.article.PhotographerInfoDto;
import com.ssafy.api.service.ArticleService;
import com.ssafy.core.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * 게시글 Controller
 *
 * @author 신민철
 * @author 서재건
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/article")
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /***
     * 게시글 등록
     * @param articlePostDto
     * @throws IOException
     */
    @PostMapping()
    public ResponseEntity<HttpStatus> uploadImage(ArticlePostDto articlePostDto) throws IOException {
        log.info(articlePostDto.toString());
        articleService.postArticle(articlePostDto);
        log.info(articlePostDto.toString());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /***
     * 작가의 아이디로 게시글 조회
     * @param photographerId
     * @return ArticleBoardDto
     */
    @GetMapping("/photographer/{photographerId}")
    public ResponseEntity<?> getPhotographerInformation(@PathVariable("photographerId") Long photographerId){
        PhotographerInfoDto photographerInfoDto = articleService.getPhotographerInformation(photographerId);
        return new ResponseEntity<>(photographerInfoDto, HttpStatus.OK);
    }

    @GetMapping("/list/{photographerId}")
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
     * @return 수정한 게시글 디테일
     * @throws IOException
     *
     *
     */
    @PutMapping("/{articleId}")
    public ResponseEntity<?> updateArticle(
            @PathVariable("articleId") Long articleId,
            ArticlePostDto articlePostDto) throws IOException{

        return ResponseEntity.ok(articleService.updateArticle(articleId, articlePostDto));
    }

    /***
     * 게시글 좋아요/ 좋아요 취소
     * @param articleId
     * @return 게시글 아이디, 좋아요 여부
     */
    @PutMapping("/heart/{articleId}")
    public ResponseEntity<?> heartArticle(
            @PathVariable("articleId") Long articleId
    ){
        ArticleHeartDto articleHeartDto = articleService.heartArticle(articleId);
        return new ResponseEntity<>(articleHeartDto, HttpStatus.OK);
    }

    /***
     * 좌상,우하 위도 경도 주어졌을 때 그 안의 게시물 List로 모두 반환
     * @param y1
     * @param x1
     * @param y2
     * @param x2
     * @return 게시글리스트
     */
    @GetMapping("list")
    public ResponseEntity<?> searchArticleNear(@RequestParam("cord1y") Double y1,
                                    @RequestParam("cord1x") Double x1,
                                    @RequestParam("cord2y") Double y2,
                                    @RequestParam("cord2x") Double x2){
        List<ArticleSearchDto> articleSearchDtoList = articleService.searchArticleNear(y1, x1, y2, x2);
        return new ResponseEntity<>(articleSearchDtoList, HttpStatus.OK);
    }

    /**
     * 카테고리 이름으로 게시글 검색
     *
     * @param categoryName
     * @return List<ArticleSearchDto>
     */
    @GetMapping("/search")
    public ResponseEntity<List<ArticleSearchDto>> searchArticleByCategory(@Param("categoryName") String categoryName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        List<ArticleSearchDto> articleSearchDto = articleService.getArticleListByCategory(user.getId(), categoryName);
        return ResponseEntity.ok().body(articleSearchDto);
    }

}