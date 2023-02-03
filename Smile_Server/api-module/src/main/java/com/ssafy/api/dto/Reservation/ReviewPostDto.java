package com.ssafy.api.dto.Reservation;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/***
 * 리뷰 등록을 위한 dto
 * @author 신민철
 */
@Builder
@Data
public class ReviewPostDto {

    private Double score;

    private String content;

    private MultipartFile image;
}
