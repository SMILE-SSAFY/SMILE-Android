package com.ssafy.api.service;

import com.ssafy.api.dto.article.ArticleBoardDto;
import com.ssafy.api.dto.article.ArticleDetailDto;
import com.ssafy.api.dto.article.ArticleListDto;
import com.ssafy.api.dto.article.ArticlePostDto;
import com.ssafy.core.entity.Article;
import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.User;
import com.ssafy.core.exception.CustomException;
import com.ssafy.core.exception.ErrorCode;
import com.ssafy.core.repository.ArticleRepository;
import com.ssafy.core.repository.PhotographerRepository;
import com.ssafy.core.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class ArticleService {
    /***
     * @author: 신민철
     */
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private S3UploaderService s3UploaderService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PhotographerRepository photographerRepository;

    /***
     *
     * @param fileName
     * @param articlePostDto
     * user 정보와 articlePostDto를 받아와 articleRepository에 save
     * @throws PHOTOGRAPHER_NOT_FOUND
     */
    public void postArticle(String fileName, ArticlePostDto articlePostDto){
        Article article = articlePostDto.toEntity();
        article.setPhotoUrls(fileName);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User user = userRepository.findByEmail(username).orElseThrow(()->new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));
        article.whoPost(user);
        articleRepository.save(article);
        System.out.println(article);
    }

    /***
     *
     * @param id 게시글 id
     * @return 게시글상세정보
     * @throws ARTICLE_NOT_FOUND
     */
    public ArticleDetailDto getArticleDetail(Long id){
        Article article = articleRepository.findById(id).orElseThrow(()->new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
        return new ArticleDetailDto(article);
    }

    /***
     *
     * @param id 게시글 id
     * @throws ARTICLE_NOT_FOUND
     */
    public void deletePost(Long id){
        Article article = articleRepository.findById(id).orElseThrow(()-> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User user = userRepository.findByEmail(username).orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
        if (article.getUser().getId() == user.getId()){
            String photoUriList = article.getPhotoUrls();
            photoUriList = photoUriList.replace("[","").replace("]","");
            List<String> photoUrls = new ArrayList<String>(Arrays.asList(photoUriList.split(",")));
            photoUrls.forEach(str -> s3UploaderService.deleteFile(str.trim()));
            articleRepository.deleteById(id);
        }
    }

    /***
     *
     * @param userId
     * @return 포토그래퍼정보 + 해당 포토그래퍼가 가진 article 게시글 전체조회
     *
     * @throws UsernameNotFoundException
     */

    public ArticleBoardDto getArticleList(Long userId) throws UsernameNotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User loggedInUser = userRepository.findByEmail(username).orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(()->new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));
        Photographer photographer = photographerRepository.findById(userId).orElseThrow(()->new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));
        List<Article> articleList = articleRepository.findByUserId(userId);
        List<ArticleListDto> articleListDtoList = new ArrayList<>();
        boolean isMe = false;
        if (loggedInUser.getId() == user.getId()){
            isMe = true;
        }

        for (Article article : articleList) {
            String photoUrls = article.getPhotoUrls().replace("[", "").replace("]", "");
            List<String> photoUrlList = new ArrayList<>(Arrays.asList(photoUrls.split(",")));
            ArticleListDto articleListDto = ArticleListDto.builder()
                    .id(article.getId())
                    .photoUrl(photoUrlList.get(0).trim())
                    .build();
            articleListDtoList.add(articleListDto);
        }

        return ArticleBoardDto.builder()
                .photographerId(userId)
                .isMe(isMe)
                .photographerName(user.getName())
                .profileImg(photographer.getProfileImg())
                .introduction(photographer.getIntroduction())
                .places(photographer.getPlaces())
                .articles(articleListDtoList)
                .build();
    }

    /***
     *
     * @param articleId
     * @param multipartFiles
     * @param articlePostDto
     * @return 수정한 게시글
     * @throws IOException
     */
    public ArticleDetailDto updateArticle(Long articleId, List<MultipartFile> multipartFiles, ArticlePostDto articlePostDto) throws IOException {

        Article article = articleRepository.findById(articleId).orElseThrow(()->new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User user = userRepository.findByEmail(username).orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
        if (article.getUser().getId() == user.getId()){
            // 이미지 지우기
            String photoUriList = article.getPhotoUrls();
            photoUriList = photoUriList.replace("[","").replace("]","");
            List<String> photoUrls = new ArrayList<String>(Arrays.asList(photoUriList.split(",")));
            photoUrls.forEach(str -> s3UploaderService.deleteFile(str.trim()));

            //새로운 이미지 업로드
            String fileName = s3UploaderService.upload(multipartFiles);

            // 나머지 수정
            article.setCategory(articlePostDto.getCategory());
            article.setLatitude(articlePostDto.getLatitude());
            article.setLongitude(articlePostDto.getLongitude());
            article.setDetailAddress(articlePostDto.getDetailAddress());
            article.setPhotoUrls(fileName);
            articleRepository.save(article);

        }
        return new ArticleDetailDto(article);
    }

}
