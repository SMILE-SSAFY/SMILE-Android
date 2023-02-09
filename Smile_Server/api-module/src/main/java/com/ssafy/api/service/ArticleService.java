package com.ssafy.api.service;

import com.ssafy.api.dto.article.*;
import com.ssafy.core.dto.ArticleQdslDto;
import com.ssafy.core.dto.ArticleSearchDto;
import com.ssafy.core.entity.Article;
import com.ssafy.core.entity.ArticleCluster;
import com.ssafy.core.entity.ArticleHeart;
import com.ssafy.core.entity.ArticleRedis;
import com.ssafy.core.entity.User;
import com.ssafy.core.exception.CustomException;
import com.ssafy.core.exception.ErrorCode;
import com.ssafy.core.repository.article.ArticleClusterRepository;
import com.ssafy.core.repository.CategoriesRepository;
import com.ssafy.core.repository.article.ArticleHeartRepository;
import com.ssafy.core.repository.article.ArticleRedisRepository;
import com.ssafy.core.repository.article.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import smile.clustering.XMeans;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/***
 * 게시글 관련 Service
 *
 * @author 신민철
 * @author 서재건
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final S3UploaderService s3UploaderService;
    private final ArticleHeartRepository articleHeartRepository;
    private final CategoriesRepository categoriesRepository;
    private final ArticleClusterRepository articleClusterRepository;
    private final ArticleRedisRepository articleRedisRepository;

    /***
     * article 생성
     *
     * @param dto 게시글 작성 dto
     * @throws IOException
     */
    public void postArticle(ArticlePostDto dto) throws IOException{
        // 이미지 리스트를 S3에 업로드 후 리턴받은 Url
        List<MultipartFile> images = dto.getImageList();
        String fileName = s3UploaderService.upload(images);

        User user = UserService.getLogInUser();

        Article article = Article.builder()
                .user(user)
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .detailAddress(dto.getDetailAddress())
                .photoUrls(fileName)
                .category(dto.getCategory())
                .createdAt(LocalDateTime.now())
                .build();

        articleRepository.save(article);
    }

    /***
     * 게시글 상세조회
     *
     * @param id 게시글 id
     * @return 게시글상세정보
     * @throws ARTICLE_NOT_FOUND 게시글 없을 때
     */
    public ArticleDetailDto getArticleDetail(Long id){
        User logInUser = UserService.getLogInUser();

        Article article = articleRepository.findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.ARTICLE_NOT_FOUND));

        User articleAuthor = article.getUser();
        // 자신이 작성한 글인지 확인
        boolean isMe = isMe(logInUser, articleAuthor);
        // 좋아요를 누른 글인지 확인
        boolean isHearted = isHearted(logInUser, article);
        Long hearts = articleHeartRepository.countByArticle(article);

        return new ArticleDetailDto().of(article, isMe, isHearted, hearts);
    }

    /***
     * 게시글 삭제
     *
     * @param id 게시글 id
     * @throws ARTICLE_NOT_FOUND 게시글 없을 때
     */
    public void deletePost(Long id){
        Article article = articleRepository.findById(id)
                .orElseThrow(()-> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
        User user = UserService.getLogInUser();

        if (article.getUser().getId() == user.getId()){
            String photoUriList = article.getPhotoUrls()
                    .replace("[","").replace("]","");
            List<String> photoUrls = new ArrayList<>(Arrays.asList(photoUriList.split(",")));
            photoUrls.forEach(str -> s3UploaderService.deleteFile(str.trim()));
            articleRepository.deleteById(id);
        } else {
            throw new CustomException(ErrorCode.FAIL_AUTHORIZATION);
        }
    }

    /***
     * 작가별 게시글 조회
     * @param photographerId 작가id
     * @return 작가별 게시글 리스트, 게시글 아이디, 게시글 첫 사진을 리스트로 리턴
     */
    public List<ArticleListDto> getArticleList(Long photographerId){
        List<Article> articleList = articleRepository.findByUserIdOrderByIdDesc(photographerId);
        List<ArticleListDto> articleListDtoList = new ArrayList<>();

        for (Article article : articleList) {
            articleListDtoList.add(new ArticleListDto().of(article));
        }
        return articleListDtoList;
    }

    /***
     * 게시글 수정
     * @param articleId 게시글id
     * @param articlePostDto 게시글작성dto
     * @return 수정한 게시글
     * @throws IOException 파일 없을 때
     */
    public ArticleDetailDto updateArticle(Long articleId, ArticlePostDto articlePostDto) throws IOException {

        Article article = articleRepository.findById(articleId).orElseThrow(()->new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
        User logInUser = UserService.getLogInUser();
        Boolean isMe = isMe(logInUser, article.getUser());
        boolean isHearted = isHearted(logInUser, article);
        Long hearts;
        hearts = articleHeartRepository.countByArticle(article);

        if (isMe){
            // 이미지 지우기
            String photoUriList = article.getPhotoUrls()
                    .replace("[","").replace("]","");
            List<String> photoUrls = new ArrayList<>(Arrays.asList(photoUriList.split(",")));
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

        } else throw new CustomException(ErrorCode.FAIL_AUTHORIZATION);

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
     * @param articleId 게시글id
     * @return 게시글 id, 게시글 좋아요 여부
     */

    public ArticleHeartDto heartArticle(Long articleId){

        User user = UserService.getLogInUser();
        Article article = articleRepository.findById(articleId).orElseThrow(()->new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
        boolean isHeart = isHearted(user, article);

        // 좋아요가 눌려있지 않으면 저장
        if(!isHeart){
            articleHeartRepository.save(new ArticleHeart(user, article));
            // 눌려 있으면 취소
        } else {
           articleHeartRepository.deleteByUserAndArticle(user, article);
        }

        return ArticleHeartDto.builder()
                .articleId(articleId)
                .isHeart(!isHeart)
                .build();
    }

    /**
     * 카테고리 이름으로 게시글 검색
     *
     * @param categoryName 카테고리 이름
     * @return List<ArticleSearchDto>
     */
    public List<ArticleSearchDto> getArticleListByCategory(String categoryName) {
        Long userId = UserService.getLogInUser().getId();

        List<String> categoryNameList = categoriesRepository.findAllCategoryNameByNameContaining(categoryName);
        if (categoryNameList.isEmpty()) {
            throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        log.info("해당 카테고리 존재");

        List<ArticleQdslDto> articleList = articleRepository.findByCategoryName(userId, categoryNameList);

        List<ArticleSearchDto> articleSearchDtoList = new ArrayList<>();
        for (ArticleQdslDto articleQuerydsl : articleList) {
            articleSearchDtoList.add(new ArticleSearchDto().of(articleQuerydsl));
        }
        return articleSearchDtoList;
    }

    /***
     * 지도 범위 내 클러스터링 후 redis에 클러스터링된 데이터를 저장
     * @param y1 좌상 위도
     * @param x1 좌상 경도
     * @param y2 우하 위도
     * @param x2 우하 경도
     * @return 마커, 마커의 위치, 마커에 포함된 게시글의 개수
     */

    public List<ArticleClusterDto> clusterTest(Double y1, Double x1, Double y2, Double x2){

        // User를 조회하고 user가 이전에 clustering한 데이터를 cache로 가지고 있을 경우 삭제
        User logInUser = UserService.getLogInUser();
        articleClusterRepository.findByUserId(logInUser.getId())
                .ifPresent(base -> articleClusterRepository.deleteByUser(logInUser));

        // 지도 범위 내의 모든 게시글을 조회
        List<Article> articleList = articleRepository
                .findAllByLatitudeBetweenAndLongitudeBetweenOrderByIdDesc(y2, y1, x1, x2);
        if (articleList.isEmpty()){
            return new ArrayList<>();
        }
        // k값 최적화 필요, 클러스터링 라이브러리 이용
        XMeans clusters = XMeans.fit(getGeoPointArray(articleList),20);

        List<ArticleClusterDto> clusterResults = new ArrayList<>();

        // 클러스터링한 데이터 내에서 마커찍기 + 마커마다 게시글 개수 return
        for (int i = 0; i < clusters.size.length-1; i++) {
            double[] centroids = clusters.centroids[i];
            double centroidX = centroids[0];
            double centroidY = centroids[1];
            if (!Double.isNaN(centroidX) &&!Double.isNaN(centroidY)) {
                ArticleClusterDto clusterDto = ArticleClusterDto.builder()
                        .clusterId((long) i)
                        .numOfCluster(clusters.size[i])
                        .centroidLat(centroids[0])
                        .centroidLng(centroids[1])
                        .build();
                clusterResults.add(clusterDto);
            }
        }

        log.info(Arrays.toString(clusters.y));
        log.info(Arrays.toString(clusters.size));

        articleRedisRepository.deleteAll();

        int listIdx = 0;
        double y = (y1+y2)/2;
        double x = (x1+x2)/2;
        // 마커별 게시글을 cache로 저장
        for (int i = 0; i < clusters.size.length-1; i ++){
            Long clusterId = (long) i;
            List<ArticleRedis> articleRedisList = new ArrayList<>();
            for (int j = 0; j < clusters.size[i]; j ++ ){

                Article article = articleList.get(listIdx);

                listIdx++;
                User articleAuthor = article.getUser();

                boolean isHearted = isHearted(logInUser, article);
                Long hearts = articleHeartRepository.countByArticle(article);
                // 위도, 경도 기반 중심좌표와 게시글의 거리 계산
                double baseLength = 111000;
                double distance = Math.sqrt(Math.pow((article.getLatitude() - y) * baseLength, 2)
                        + Math.pow(Math.cos(article.getLongitude() - x) * baseLength, 2));

                String photoUrls = article.getPhotoUrls().replace("[", "").replace("]", "");
                List<String> photoUrlList = new ArrayList<>(Arrays.asList(photoUrls.split(",")));

                ArticleRedis articleRedis = ArticleRedis.builder()
                        .id(article.getId())
                        .clusterId(clusterId)
                        .photographerName(articleAuthor.getName())
                        .latitude(article.getLatitude())
                        .longitude(article.getLongitude())
                        .distance(distance)
                        .detailAddress(article.getDetailAddress())
                        .isHeart(isHearted)
                        .hearts(hearts)
                        .createdAt(article.getCreatedAt())
                        .category(article.getCategory())
                        .photoUrl(photoUrlList.get(0).trim())
                        .build();
                articleRedisList.add(articleRedis);

                articleRedisRepository.save(articleRedis);
                log.info(articleRedisList.toString());
            }
            // 해당 유저가 만든 클러스터를 cache로 저장
            ArticleCluster articleCluster = ArticleCluster.builder()
                    .id(clusterId)
                    .userId(logInUser.getId())
                    .build();
            articleClusterRepository.save(articleCluster);
        }

        log.info(Arrays.deepToString((clusters.centroids)));
        log.info(clusterResults.toString());

        return clusterResults;
    }

    /***
     * 클러스터링 이후 해당 클러스터 별로 cache를 조회하여 게시글 검색결과를 반환
     * @param clusterId 클러스터 마커 id
     * @param condition distance, title, heart 순으로 조회 가능ㄷ
     * @param pageId 늘어날 수록 조회하는 게시글 증가
     * @return 마커별 게시글 리스트
     */

    public ArticleClusterListDto getArticleListByMarkerId(Long clusterId, String condition, Long pageId){
        log.info(condition);
        Boolean isEndPage = false;
        // 최신순 조회
        switch (condition) {
            case "time": {
                List<ArticleRedis> articleRedisPage = articleRedisRepository.findAllByClusterIdOrderByIdDesc(clusterId);
                doCluster(articleRedisPage, pageId, isEndPage);
                break;
                // 좋아요순 조회
            }
            case "heart": {
                List<ArticleRedis> articleRedisPage = articleRedisRepository.findAllByClusterIdOrderByHeartsDesc(clusterId);
                doCluster(articleRedisPage, pageId, isEndPage);
                break;
            }
                // 거리순 조회
            case "distance": {
                List<ArticleRedis> articleRedisPage = articleRedisRepository.findAllByClusterIdOrderByDistanceAsc(clusterId);
                doCluster(articleRedisPage, pageId, isEndPage);
                break;
            }
        }
        return ArticleClusterListDto.builder().build();
    }

    /***
     * 클러스터를 마커 별로 paging
     * @param articleRedisPage
     * @param pageId
     * @param isEndPage
     * @return
     */
    private ArticleClusterListDto doCluster(List<ArticleRedis> articleRedisPage, Long pageId, Boolean isEndPage){
        log.info(articleRedisPage.toString());
        log.info(String.valueOf((int) ((pageId+1)*9)));

        Integer size = (int) ((pageId+1)*9);
        // cache를 paging
        if (size > articleRedisPage.size()){
            size = articleRedisPage.size();
            isEndPage = true;
        }
        if (size<9){
            return ArticleClusterListDto.builder()
                    .isEndPage(isEndPage)
                    .articleRedisList(articleRedisPage)
                    .build();
        }
        return ArticleClusterListDto.builder()
                .isEndPage(isEndPage)
                .articleRedisList(articleRedisPage.subList(size-9,size))
                .build();
    }

    /***
     * 내가 좋아요 누른 게시글 목록
     * @return 내가 좋아요 누른 게시글 목록
     */
    public List<ArticleSearchDto> getHeartedArticle(){
        User user = UserService.getLogInUser();
        List<ArticleHeart> articleHeartList = articleHeartRepository.findByUser(user);

        List<ArticleSearchDto> results = new ArrayList<>();

        for(ArticleHeart articleHeart : articleHeartList){

            Article article = articleHeart.getArticle();
            User articleAuthor = article.getUser();
            Long hearts = articleHeartRepository.countByArticle(article);
            String photoUrls = article.getPhotoUrls().replace("[", "").replace("]", "");
            List<String> photoUrlList = new ArrayList<>(Arrays.asList(photoUrls.split(",")));

            ArticleSearchDto articleSearchDto = ArticleSearchDto.builder()
                    .articleId(article.getId())
                    .photographerName(articleAuthor.getName())
                    .latitude(article.getLatitude())
                    .longitude(article.getLongitude())
                    .detailAddress(article.getDetailAddress())
                    .isHeart(true)
                    .hearts(hearts)
                    .createdAt(article.getCreatedAt())
                    .category(article.getCategory())
                    .photoUrl(photoUrlList.get(0).trim())
                    .build();
            results.add(articleSearchDto);
        }

        return results;
    }

    /***
     *
     * @param user 유저
     * @param article 게시글
     * @return 해당 게시글의 좋아요 여부
     */
    private boolean isHearted(User user, Article article){
        return articleHeartRepository.findByUserAndArticle(user, article).isPresent();
    }

    /***
     * 게시글의 작성자와 현재 로그인한 유저가 일치하는지에 대한 여부
     * @param user 유저
     * @param logInUser 로그인한 유저
     * @return 유저 일치 여부
     */
    public boolean isMe(User user, User logInUser){
        return logInUser.getId() == user.getId();
    }

    /***
     * 지도의 범위내 게시글에서 위도 경도를 얻어오는 함수
     * @param articleList 범위내의 모든 게시글
     * @return 범위내 게시글을 double[][]로 위도경도로 리턴
     */
     private double[][] getGeoPointArray(List<Article> articleList){
         if (articleList.isEmpty()){
             return new double[0][];
         }
         double[][] geoPointArray = new double[articleList.size()][];
         int index = 0;
         for (Article article : articleList){
             geoPointArray[index++] = new double[]{article.getLatitude(), article.getLongitude()};
         }
         return geoPointArray;
     }
}
