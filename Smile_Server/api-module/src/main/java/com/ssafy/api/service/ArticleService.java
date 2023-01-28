package com.ssafy.api.service;

import com.ssafy.api.dto.article.*;
import com.ssafy.core.entity.Article;
import com.ssafy.core.entity.ArticleHeart;
import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.PhotographerNCategories;
import com.ssafy.core.entity.PhotographerNPlaces;
import com.ssafy.core.entity.User;
import com.ssafy.core.exception.CustomException;
import com.ssafy.core.exception.ErrorCode;
import com.ssafy.core.repository.ArticleHeartRepository;
import com.ssafy.core.repository.ArticleRepository;
import com.ssafy.core.repository.PhotographerHeartRepository;
import com.ssafy.core.repository.PhotographerRepository;
import com.ssafy.core.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/***
 * @author 신민철
 */
@Service
@Slf4j
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final S3UploaderService s3UploaderService;
    private final UserRepository userRepository;
    private final PhotographerRepository photographerRepository;
    private final ArticleHeartRepository articleHeartRepository;
    private final PhotographerHeartRepository photographerHeartRepository;

    public ArticleService(ArticleRepository articleRepository, S3UploaderService s3UploaderService, UserRepository userRepository, PhotographerRepository photographerRepository, ArticleHeartRepository articleHeartRepository, PhotographerHeartRepository photographerHeartRepository) {
        this.articleRepository = articleRepository;
        this.s3UploaderService = s3UploaderService;
        this.userRepository = userRepository;
        this.photographerRepository = photographerRepository;
        this.articleHeartRepository = articleHeartRepository;
        this.photographerHeartRepository = photographerHeartRepository;
    }

    /***
     * article 생성
     *
     * @param dto
     * @throws PHOTOGRAPHER_NOT_FOUND
     */
    public void postArticle(ArticlePostDto dto) throws IOException{
        List<MultipartFile> images = dto.getImageList();
        String fileName = s3UploaderService.upload(images);
        Article article = dto.toEntity();
        article.setPhotoUrls(fileName);
        article.setCreatedAt(LocalDateTime.now());
        User user = getLogInUser();
        article.whoPost(user);
        articleRepository.save(article);
    }

    /***
     *
     * @param id 게시글 id
     * @return 게시글상세정보
     * @throws ARTICLE_NOT_FOUND
     */
    public ArticleDetailDto getArticleDetail(Long id){
        User logInUser = getLogInUser();
        Article article = articleRepository.findById(id).orElseThrow(()->new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
        User articleAuthor = article.getUser();
        boolean isMe = isMe(logInUser, articleAuthor);
        boolean isHearted = isHearted(logInUser, article);
        Long hearts = articleHeartRepository.countByArticle(article);

        return ArticleDetailDto.builder()
                .id(id)
                .isMe(isMe)
                .isHeart(isHearted)
                .detailAddress(article.getDetailAddress())
                .category(article.getCategory())
                .createdAt(article.getCreatedAt())
                .photoUrls(article.getPhotoUrls())
                .hearts(hearts)
                .build();
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
            List<String> photoUrls = new ArrayList<>(Arrays.asList(photoUriList.split(",")));
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

    @Transactional
    public PhotographerInfoDto getPhotographerInformation(Long userId) {
        User logInUser = getLogInUser();
        User user = userRepository.findById(userId).orElseThrow(()->new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));
        Photographer photographer = photographerRepository.findById(userId).orElseThrow(()->new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));
        Boolean isMe = isMe(logInUser, user);
        Boolean isHeart = photographerHeartRepository.findByUserAndPhotographer(logInUser, photographer).isPresent();
        Long hearts = photographerHeartRepository.countByPhotographer(photographer);
        // 활동지역
        List<String> places = new ArrayList<>();
        for(PhotographerNPlaces place : photographer.getPlaces()){
            places.add(place.getPlaces().getFirst()+" " +place.getPlaces().getSecond());
        }

        // 카테고리
        List<String> categories = new ArrayList<>();
        for(PhotographerNCategories category : photographer.getCategories()){
            categories.add(category.getCategory().getName());
        }

        return PhotographerInfoDto.builder()
                .photographerId(userId)
                .isMe(isMe)
                .isHeart(isHeart)
                .hearts(hearts)
                .photographerName(user.getName())
                .profileImg(photographer.getProfileImg())
                .introduction(photographer.getIntroduction())
                .categories(categories)
                .places(places)
                .build();
    }

    /***
     * 작가별 게시글 조회
     * @param photographerId
     * @return 작가별 게시글 리스트, 게시글 아이디, 게시글 첫 사진을 리스트로 리턴
     */
    public List<ArticleListDto> getArticleList(Long photographerId){
        List<Article> articleList = articleRepository.findByUserId(photographerId);
        List<ArticleListDto> articleListDtoList = new ArrayList<>();

        for (Article article : articleList) {
            String photoUrls = article.getPhotoUrls().replace("[", "").replace("]", "");
            List<String> photoUrlList = new ArrayList<>(Arrays.asList(photoUrls.split(",")));
            ArticleListDto articleListDto = ArticleListDto.builder()
                    .id(article.getId())
                    .photoUrl(photoUrlList.get(0).trim())
                    .build();
            articleListDtoList.add(articleListDto);
        }
        return articleListDtoList;
    }

    /***
     *
     * @param articleId
     * @param articlePostDto
     * @return 수정한 게시글
     * @throws IOException
     */
    public ArticleDetailDto updateArticle(Long articleId, ArticlePostDto articlePostDto) throws IOException {

        Article article = articleRepository.findById(articleId).orElseThrow(()->new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
        User logInUser = getLogInUser();
        Boolean isMe = isMe(logInUser, article.getUser());
        boolean isHearted = isHearted(logInUser, article);
        Long hearts;
        hearts = articleHeartRepository.countByArticle(article);

        if (isMe){
            // 이미지 지우기
            String photoUriList = article.getPhotoUrls();
            photoUriList = photoUriList.replace("[","").replace("]","");
            List<String> photoUrls = new ArrayList<String>(Arrays.asList(photoUriList.split(",")));
            photoUrls.forEach(str -> s3UploaderService.deleteFile(str.trim()));

            //새로운 이미지 업로드
            List<MultipartFile> images = articlePostDto.getImageList();
            String fileName = s3UploaderService.upload(images);

            // 나머지 수정
            article.setCategory(articlePostDto.getCategory());
            article.setLatitude(articlePostDto.getLatitude());
            article.setLongitude(articlePostDto.getLongitude());
            article.setDetailAddress(articlePostDto.getDetailAddress());
            article.setPhotoUrls(fileName);
            articleRepository.save(article);

        }
        return ArticleDetailDto.builder()
                .id(articleId)
                .isMe(isMe)
                .isHeart(isHearted)
                .detailAddress(article.getDetailAddress())
                .category(article.getCategory())
                .createdAt(article.getCreatedAt())
                .photoUrls(article.getPhotoUrls())
                .hearts(hearts)
                .build();
    }

    /***
     * 게시글 좋아요
     * @param articleId
     * @return 게시글 id, 게시글 좋아요 여부
     */

    public ArticleHeartDto heartArticle(Long articleId){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        User user = userRepository.findByEmail(username).orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND));
        Article article = articleRepository.findById(articleId).orElseThrow(()->new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
        boolean isHeart = isHearted(user, article);
        if(!isHeart){
            articleHeartRepository.save(new ArticleHeart(user, article));
        } else {
           articleHeartRepository.deleteByUserAndArticle(user, article);
        }

        return ArticleHeartDto.builder()
                .articleId(articleId)
                .isHeart(!isHeart)
                .build();
    }

    /***
     * 범위안의 게시글을 리턴
     * @param y1
     * @param x1
     * @param y2
     * @param x2
     * @return 게시글 리스트
     */
    public List<ArticleSearchDto> searchArticleNear(Double y1, Double x1, Double y2, Double x2){

        List<Article> articleList = articleRepository.findAllByLatitudeBetweenAndLongitudeBetween(y1, y2, x1, x2);
        List<ArticleSearchDto> articleSearchDtoList = new ArrayList<>();
        User logInUser = getLogInUser();

        // 각각의 게시글을 Dto로 만들어서 List에 등록
        for (Article article : articleList) {

            Long articleId = article.getId();
            User articleAuthor = article.getUser();
            boolean isHearted = isHearted(logInUser, article);
            Long hearts = articleHeartRepository.countByArticle(article);

            String photoUrls = article.getPhotoUrls().replace("[", "").replace("]", "");
            List<String> photoUrlList = new ArrayList<>(Arrays.asList(photoUrls.split(",")));

            ArticleSearchDto articleSearchDto = ArticleSearchDto.builder()
                    .articleId(articleId)
                    .photographerName(articleAuthor.getName())
                    .latitude(article.getLatitude())
                    .longitude(article.getLongitude())
                    .detailAddress(article.getDetailAddress())
                    .isHeart(isHearted)
                    .hearts(hearts)
                    .category(article.getCategory())
                    .photoUrl(photoUrlList.get(0).trim())
                    .build();

            articleSearchDtoList.add(articleSearchDto);
        }
        return articleSearchDtoList;
    }

    /***
     *
     * @param user
     * @param article
     * @return 해당 게시글의 좋아요 여부
     */
    private boolean isHearted(User user, Article article){
        return articleHeartRepository.findByUserAndArticle(user, article).isPresent();
    }

    /***
     * 게시글의 작성자와 현재 로그인한 유저가 일치하는지에 대한 여부
     * @param user
     * @param logInUser
     * @return 유저 일치 여부
     */
    private boolean isMe(User user, User logInUser){
        if (logInUser.getId() == user.getId()){
            return true;
        }
        return false;
    }

    /***
     * 로그인한 유저를 얻어오는 함수
     * @return user
     */
    private User getLogInUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User logInUser = userRepository.findByEmail(username).orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND));
        return logInUser;
    }


}
