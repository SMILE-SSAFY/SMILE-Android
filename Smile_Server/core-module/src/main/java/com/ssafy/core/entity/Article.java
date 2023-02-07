package com.ssafy.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시글 Entity
 *
 * @author 신민철
 * @author 서재건
 */
@Entity
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private Double latitude;

    private Double longitude;

    private String detailAddress;

    private LocalDateTime createdAt;

    private String category;

    private String photoUrls;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<ArticleHeart> hearts;

}
