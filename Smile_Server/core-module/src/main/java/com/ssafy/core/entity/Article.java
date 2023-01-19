package com.ssafy.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

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

//    private Integer heart;

    @CreatedDate
    private LocalDateTime createdAt;

    private String category;

    private String photoUrls;

    public void whoPost(User user){
        this.user = user;
    }

}
