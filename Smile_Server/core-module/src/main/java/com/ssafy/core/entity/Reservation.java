package com.ssafy.core.entity;

import com.ssafy.core.code.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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

    @ManyToOne
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Photographer photographer;

    @ManyToOne
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private User user;

    @ColumnDefault("0")
    @Enumerated(EnumType.ORDINAL)
    @Builder.Default
    private ReservationStatus status = ReservationStatus.예약확정전;

    private int price;

    // 만나는 장소
    private String place;

    private Date date;

    private Time time;

    @Column(name = "reviewed")
    @ColumnDefault("false")
    private boolean isReviewed;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}