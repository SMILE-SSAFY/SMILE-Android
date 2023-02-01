package com.ssafy.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/***
 * 리뷰 엔티티
 * @author 신민철
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer score;

    private String content;

    private String PhotoUrl;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservationId;

    @ManyToOne
    @JoinColumn(name = "photographer_id")
    private Photographer photographerId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
}
