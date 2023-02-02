package com.ssafy.api.service;

import com.ssafy.api.dto.article.ArticleClusterDto;
import com.ssafy.api.dto.article.ArticleClusterListDto;
import com.ssafy.api.dto.article.ArticleDetailDto;
import com.ssafy.api.dto.article.ArticleHeartDto;
import com.ssafy.api.dto.article.ArticleListDto;
import com.ssafy.api.dto.article.ArticlePostDto;
import com.ssafy.api.dto.article.PhotographerInfoDto;
import com.ssafy.core.dto.ArticleQdslDto;
import com.ssafy.core.dto.ArticleSearchDto;
import com.ssafy.core.entity.Article;
import com.ssafy.core.entity.ArticleCluster;
import com.ssafy.core.entity.ArticleHeart;
import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.PhotographerNCategories;
import com.ssafy.core.entity.PhotographerNPlaces;
import com.ssafy.core.entity.User;
import com.ssafy.core.exception.CustomException;
import com.ssafy.core.exception.ErrorCode;
import com.ssafy.core.repository.ArticleClusterRepository;
import com.ssafy.core.repository.CategoriesRepository;
import com.ssafy.core.repository.UserRepository;
import com.ssafy.core.repository.article.ArticleHeartRepository;
import com.ssafy.core.repository.article.ArticleRepository;
import com.ssafy.core.repository.photographer.PhotographerHeartRepository;
import com.ssafy.core.repository.photographer.PhotographerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import smile.clustering.KMeans;
import smile.clustering.PartitionClustering;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/***
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
    private final UserRepository userRepository;
    private final PhotographerRepository photographerRepository;
    private final ArticleHeartRepository articleHeartRepository;
    private final PhotographerHeartRepository photographerHeartRepository;
    private final CategoriesRepository categoriesRepository;
    private final ArticleClusterRepository articleClusterRepository;

    /***
     * article 생성
     *
     * @param dto 게시글 작성 dto
     * @throws PHOTOGRAPHER_NOT_FOUND 사진작가 없을 때
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
     * 게시글 상세조회
     * @param id 게시글 id
     * @return 게시글상세정보
     * @throws ARTICLE_NOT_FOUND 게시글 없을 때
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
                .photographerName(articleAuthor.getName())
                .build();
    }

    /***
     * 게시글 삭제
     * @param id 게시글 id
     * @throws ARTICLE_NOT_FOUND 게시글 없을 때
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
        } else {
            throw new CustomException(ErrorCode.USER_MISMATCH);
        }
    }

    /***
     * 포트폴리오의 작가정보
     * @param userId 유저 아이디
     * @return 포토그래퍼정보 + 해당 포토그래퍼가 가진 article 게시글 전체조회
     *
     * @throws UsernameNotFoundException 유저 없을 때
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
     * @param photographerId 작가id
     * @return 작가별 게시글 리스트, 게시글 아이디, 게시글 첫 사진을 리스트로 리턴
     */
    public List<ArticleListDto> getArticleList(Long photographerId){
        List<Article> articleList = articleRepository.findByUserIdOrderByIdDesc(photographerId);
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
     * 게시글 수정
     * @param articleId 게시글id
     * @param articlePostDto 게시글작성dto
     * @return 수정한 게시글
     * @throws IOException 파일 없을 때
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

        } else throw new CustomException(ErrorCode.USER_MISMATCH);

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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        User user = userRepository.findByEmail(username).orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND));
        Article article = articleRepository.findById(articleId).orElseThrow(()->new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
        boolean isHeart = isHearted(user, article);
        log.info(String.valueOf(isHeart));
        log.info(String.valueOf(article));
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

    /**
     * 카테고리 이름으로 게시글 검색
     *
     * @param userId 유저id
     * @param categoryName 카테고리 이름
     * @return List<ArticleSearchDto>
     */
    public List<ArticleSearchDto> getArticleListByCategory(Long userId, String categoryName) {
        List<String> categoryNameList = categoriesRepository.findAllCategoryNameByNameContaining(categoryName);
        if (categoryNameList.isEmpty()) {
            throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        log.info("해당 카테고리 존재");

        List<ArticleQdslDto> articleList = articleRepository.findByCategoryName(userId, categoryNameList);

        List<ArticleSearchDto> articleSearchDtoList = new ArrayList<>();
        for (ArticleQdslDto articleQuerydsl : articleList) {
            String photoUrls = articleQuerydsl.getArticle().getPhotoUrls().replace("[", "").replace("]", "");
            List<String> photoUrlList = new ArrayList<>(Arrays.asList(photoUrls.split(",")));

            ArticleSearchDto articleSearchDto = ArticleSearchDto.builder()
                    .articleId(articleQuerydsl.getArticle().getId())
                    .photographerName(articleQuerydsl.getArticle().getUser().getName())
                    .latitude(articleQuerydsl.getArticle().getLatitude())
                    .longitude(articleQuerydsl.getArticle().getLongitude())
                    .detailAddress(articleQuerydsl.getArticle().getDetailAddress())
                    .isHeart(articleQuerydsl.isHeart())
                    .hearts(articleQuerydsl.getHearts())
                    .createdAt(articleQuerydsl.getArticle().getCreatedAt())
                    .category(articleQuerydsl.getArticle().getCategory())
                    .photoUrl(photoUrlList.get(0).trim())
                    .build();

            articleSearchDtoList.add(articleSearchDto);
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
        articleClusterRepository.deleteAll();

        List<Article> articleList = articleRepository.findAllByLatitudeBetweenAndLongitudeBetweenOrderByIdDesc(y2, y1, x1, x2);
        if (articleList.isEmpty()){
            return new ArrayList<>();
        }
        // k값 최적화 필요
        KMeans clusters = PartitionClustering.run(20, ()->KMeans.fit(getGeoPointArray(articleList),2));

        List<ArticleClusterDto> clusterResults = new ArrayList<>();

        User logInUser = getLogInUser();

        for (int i = 0; i < clusters.size.length-1; i++) {
            double[] centroids = clusters.centroids[i];
            Double centroidX = centroids[0];
            Double centroidY = centroids[1];
            if (!centroidX.isNaN()&&!centroidY.isNaN()) {
                ArticleClusterDto clusterDto = ArticleClusterDto.builder()
                        .clusterId(Long.valueOf(i))
                        .numOfCluster(clusters.size[i])
                        .centroidLat(centroids[0])
                        .centroidLng(centroids[1])
                        .build();
                clusterResults.add(clusterDto);
            }
        }

        log.info(Arrays.toString(clusters.y));
        log.info(Arrays.toString(clusters.size));

        int clusterIdx = 0;
        for (int i = 0; i < clusters.size.length-1; i ++){
            Long clusterId = (long) i;
            List<ArticleSearchDto> articleSearchDtoList = new ArrayList<>();
            for (int j = 0; j < clusters.size[i]; j ++ ){

                Article article = articleList.get(clusterIdx);

                clusterIdx++;
                User articleAuthor = article.getUser();

                boolean isHearted = isHearted(logInUser, article);
                Long hearts = articleHeartRepository.countByArticle(article);

                String photoUrls = article.getPhotoUrls().replace("[", "").replace("]", "");
                List<String> photoUrlList = new ArrayList<>(Arrays.asList(photoUrls.split(",")));

                ArticleSearchDto articleSearchDto = ArticleSearchDto.builder()
                        .articleId(article.getId())
                        .photographerName(articleAuthor.getName())
                        .latitude(article.getLatitude())
                        .longitude(article.getLongitude())
                        .detailAddress(article.getDetailAddress())
                        .isHeart(isHearted)
                        .hearts(hearts)
                        .createdAt(article.getCreatedAt())
                        .category(article.getCategory())
                        .photoUrl(photoUrlList.get(0).trim())
                        .build();
                articleSearchDtoList.add(articleSearchDto);
            }
            ArticleCluster articleCluster = ArticleCluster.builder()
                    .Id(clusterId)
                    .articleSearchDtoList(articleSearchDtoList)
                    .build();
            articleClusterRepository.save(articleCluster);
        }

        log.info(Arrays.deepToString((clusters.centroids)));
        log.info(clusterResults.toString());
        log.info(articleClusterRepository.findAll().toString());


        return clusterResults;
    }

    /***
     * 클러스터링 이후 해당 클러스터 별로 게시글 검색결과를 반환
     * @param Id 클러스터 마커 id
     * @return 마커별 게시글 리스트
     */

    public ArticleClusterListDto getClusterByClusterId(Long Id){
        ArticleCluster articleCluster = articleClusterRepository.findById(Id).orElseThrow();
        return ArticleClusterListDto.builder()
                .clusterId(Id)
                .articleSearchDtoList(articleCluster.getArticleSearchDtoList())
                .build();
    }

    /***
     * 내가 좋아요 누른 게시글 목록
     * @return 내가 좋아요 누른 게시글 목록
     */
    public List<ArticleSearchDto> getHeartedArticle(){
        User user = getLogInUser();
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
    private boolean isMe(User user, User logInUser){
        return logInUser.getId() == user.getId();
    }

    /***
     * 로그인한 유저를 얻어오는 함수
     * @return user
     */
    private User getLogInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        return user;
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
