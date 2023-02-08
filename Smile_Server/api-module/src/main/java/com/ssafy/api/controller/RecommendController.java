package com.ssafy.api.controller;

import com.ssafy.api.dto.Photographer.PhotographerForListDto;
import com.ssafy.api.dto.Photographer.PhotographerResDto;
import com.ssafy.api.dto.recommend.RecommendRequestDto;
import com.ssafy.api.dto.recommend.RecommendResponseDto;
import com.ssafy.api.service.PhotographerService;
import com.ssafy.api.service.RecommendService;

import com.ssafy.core.dto.PhotographerIdQdslDto;
import com.ssafy.core.entity.PhotographerHeart;
import com.ssafy.core.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();

        RecommendResponseDto resultList = recommendService.recommendPhotographerByAddress(user, address);

        return ResponseEntity.ok().body(resultList);
    }

}
