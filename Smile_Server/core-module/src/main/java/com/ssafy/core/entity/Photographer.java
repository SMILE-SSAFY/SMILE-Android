package com.ssafy.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * 사진작가 Entity
 *
 * @author 김정은
 * @author 서재건
 * @author 신민철
 */
@Entity
@Table(name = "photographer")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Photographer implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @MapsId
    private User user;

    @Column(name = "profile_img")
    private String profileImg;

    @Column(length = 100, nullable = false)
    private String introduction;

    @Column(length = 35, nullable = false)
    private String account;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "photographer")
    private List<PhotographerNPlaces> places;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "photographer")
    private List<PhotographerNCategories> categories;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "photographer")
    private List<PhotographerHeart> hearts;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "photographer")
    private List<Reservation> reservations;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "photographer")
    private List<Review> reviews;

    /**
     * 프로필 이미지 변경
     *
     * @param profileImg
     */
    public void updateProfileImg(String profileImg){
        this.profileImg = profileImg;
    }

    /**
     * 프로필 소개 변경
     *
     * @param introduction
     */
    public void updateIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * 프로필 계좌 변경
     *
     * @param account
     */
    public void updateAccount(String account) {
        this.account = account;
    }

    /**
     * 프로필 활동지역 변경
     *
     * @param places
     */
    public void updatePlaces(List<PhotographerNPlaces> places) {
        this.places = places;
    }

    /**
     * 프로필 카테고리 정보 변경
     *
     * @param categories
     */
    public void updateCategories(List<PhotographerNCategories> categories){
        this.categories = categories;
    }
}
