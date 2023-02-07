package com.ssafy.api.dto.article;

import com.ssafy.core.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/***
 * 작가별 게시글 조회에서 id 하나의 photoUrl만 돌려주기 위한 Dto
 *
 * @author 신민철
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListDto {

    private Long id;
    private String photoUrl;

    public ArticleListDto of(Article article){
        String photoUrls = article.getPhotoUrls().replace("[", "").replace("]", "");
        List<String> photoUrlList = new ArrayList<>(Arrays.asList(photoUrls.split(",")));

        return ArticleListDto.builder()
                .id(article.getId())
                .photoUrl(photoUrlList.get(0).trim())
                .build();
    }
}
