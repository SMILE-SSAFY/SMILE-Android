package com.ssafy.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Photographer photographer;

    private Double latitude;

    private Double longitude;

    private String detailAddress;

    private Integer heart;

    @CreatedDate
    private LocalDateTime createdAt;

//    @Enumerated(value = EnumType.STRING)
//    private Category category;

    private String photoUrls;

    public void whoPost(Photographer photographer){
        this.photographer = photographer;
    }

}
