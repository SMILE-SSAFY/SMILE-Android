package com.ssafy.core.entity;

import com.ssafy.core.code.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photographer_id")
    private Photographer photographer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ColumnDefault("0")
    @Enumerated(EnumType.ORDINAL)
    @Builder.Default
    private ReservationStatus status = ReservationStatus.예약확정전;

    private int price;

    private String email;

    // 만나는 장소
    private String place;

    private Date reservedAt;

    private Time reservedTime;

    @Column(name = "reviewed")
    @ColumnDefault("false")
    private boolean isReviewed;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}