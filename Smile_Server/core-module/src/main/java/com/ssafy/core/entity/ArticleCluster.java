package com.ssafy.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/***
 * @author 신민철
 * redis에 cache로 article 정보를 저장
 */
@Entity
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class ArticleCluster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long articleId;

    private Long clusterId;

    private Long userId;

    private Long photographerId;

    private String photographerName;

    private Double latitude;

    private Double longitude;

    private Double distance;

    private Boolean isHeart;

    private Long hearts;

    private String detailAddress;

    private LocalDateTime createdAt;

    private String category;

    private String photoUrl;

}
