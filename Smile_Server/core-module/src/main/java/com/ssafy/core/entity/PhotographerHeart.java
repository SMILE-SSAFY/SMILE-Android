package com.ssafy.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 사진 작가 좋아요 Entity
 *
 * @author 신민철
 * @author 서재건
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PhotographerHeart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "photographer_id")
    private Photographer photographer;

    public PhotographerHeart(User user, Photographer photographer){
        this.user = user;
        this.photographer = photographer;
    }
}
