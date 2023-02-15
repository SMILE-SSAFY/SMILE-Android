package com.ssafy.api.controller;

import com.ssafy.api.dto.recommend.RecommendResponseDto;
import com.ssafy.api.service.RecommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 추천 관련 Controller
 *
 * @author 이지윤
 */

@RestController
@RequestMapping("/api/recommend")
@Slf4j
public class RecommendController {

    @Autowired
    private RecommendService recommendService;


    /**
     * 사용자의 현재 주소 기반, 작가 추천
     *
     * @param address
     * @return ResponseEntity<List<PhotographerForListDto>>
     *
     *
     */
    @GetMapping
    public ResponseEntity<RecommendResponseDto> recommendPhotographerByAddress(@Param("address") String address){
        return ResponseEntity.ok().body(recommendService.recommendPhotographerByAddress(address));
    }

}
