package com.ssafy.core.entity;

import com.sun.istack.NotNull;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @NotNull
    @Column(name = "profile_img")
    private String profileImg;

    @NotNull
    @Column(length = 100)
    private String introduction;

    @NotNull
    @Column(length = 14)
    private String account;

    @NotNull
    private int heart;

    @NotNull
    @Column(name = "places", columnDefinition = "longtext")
    @Type(type="json")
    private List<Places> places;

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
}
