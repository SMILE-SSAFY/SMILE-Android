package com.ssafy.core.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * 사진작가 Entity
 *
 * author @김정은
 */
@Entity
@Table(name = "photographer")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@TypeDef(name = "json", typeClass = JsonType.class)
public class Photographer implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    @MapsId
    private User user;

    @Column(name = "profile_img", nullable = false)
    private String profileImg;

    @Column(length = 100, nullable = false)
    private String introduction;

    @Column(length = 14, nullable = false)
    private String account;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int heart;

    @Column(columnDefinition = "longtext", nullable = false)
    @Type(type="json")
    private List<Places> places;

    @Column(columnDefinition = "longtext", nullable = false)
    @Type(type="json")
    private List<Categories> categories;

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
     * 프로필 좋아요 변경
     *
     * @param heart
     */
    public void updateHeart(int heart) {
        this.heart = heart;
    }

    /**
     * 프로필 활동지역 변경
     *
     * @param places
     */
    public void updatePlaces(List<Places> places) {
        this.places = places;
    }

    /**
     * 프로필 카테고리 정보 변경
     *
     * @param categories
     */
    public void updateCategories(List<Categories> categories){
        this.categories = categories;
    }
}
