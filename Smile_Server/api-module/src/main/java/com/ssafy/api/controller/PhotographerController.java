package com.ssafy.api.controller;


import com.ssafy.api.dto.Photographer.PhotographerForListDto;
import com.ssafy.api.dto.Photographer.PhotographerHeartDto;
import com.ssafy.api.dto.Photographer.PhotographerReqDto;
import com.ssafy.api.dto.Photographer.PhotographerResDto;
import com.ssafy.api.dto.Photographer.PhotographerUpdateReqDto;
import com.ssafy.api.service.PhotographerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 작가 관련 Controller
 *
 * @author 김정은
 * @author 서재건
 * @author 신민철
 */
@RestController
@RequestMapping("/api/photographer")
public class PhotographerController {

    @Autowired
    private PhotographerService photographerService;

    /**
     * 작가 프로필 등록
     *
     * @param photographer
     * @param multipartFile
     * @return 정상일 때 OK
     */
    @PostMapping
    public ResponseEntity<HttpStatus> registerPhotographer(
            @RequestPart("photographer") PhotographerReqDto photographer,
            @RequestPart("profileImg") MultipartFile multipartFile) throws IOException {
        photographerService.addPhotographer(multipartFile, photographer);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * 작가 프로필 조회
     *
     * @return 작가 프로필 객체
     */
    @GetMapping
    public ResponseEntity<PhotographerResDto> getPhotographer(){
        return ResponseEntity.ok(photographerService.getPhotographer());
    }

    /**
     * 작가 프로필 수정
     *
     * @param photographer
     * @param multipartFile
     * @return 수정된 작가 프로필 객체
     */
    @PutMapping
    public ResponseEntity<PhotographerResDto> changePhotographerInfo(
            @RequestPart("Photographer") PhotographerUpdateReqDto photographer,
            @RequestPart("profileImg") MultipartFile multipartFile) throws IOException{
        return ResponseEntity.ok(photographerService.changePhotographer(multipartFile, photographer));
    }


    /**
     * 작가 프로필 삭제
     *
     * @return 삭제 성공 시 OK
     */
    @DeleteMapping
    public ResponseEntity<HttpStatus> removePhotographer(){
        photographerService.removePhotographer();
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * 카테고리로 작가 검색
     *
     * @param categoryName
     * @return List<PhotographerForListDto>
     */
    @GetMapping("/search")
    public ResponseEntity<List<PhotographerForListDto>> searchPhotographerByCategory(@Param("categoryName") String categoryName) {
        List<PhotographerForListDto> photographerList =
                photographerService.getPhotographerListByCategory(categoryName);
        return ResponseEntity.ok().body(photographerList);
    }

    /***
     *
     * @param photographerId
     * @return 작가 id, isHeart boolean
     */
    @PutMapping("/heart/{photographerId}")
    public ResponseEntity<?> addHeartPhotographer(@PathVariable("photographerId") Long photographerId){
        PhotographerHeartDto photographerHeartDto = photographerService.addHeartPhotographer(photographerId);
        return new ResponseEntity<>(photographerHeartDto, HttpStatus.OK);
    }

    /**
     * 주변 작가 조회
     *
     * @param address 검색할 주소
     * @param criteria 정렬 기준
     * @return List<PhotographerForListDto>
     */
    @GetMapping("/list")
    public ResponseEntity<List<PhotographerForListDto>> searchPhotographerByAddress(
            @Param("address") String address, @Param("criteria") String criteria) {
        List<PhotographerForListDto> photographerList =
                photographerService.getPhotographerListByAddresss(address, criteria);
        return ResponseEntity.ok().body(photographerList);
    }

    /***
     * 내가 좋아요 누른 작가 조회
     * @return photographerList 내가 좋아요 누른 작가 조회
     */
    @GetMapping("/heart/list")
    public ResponseEntity<?> getHeartedPhotographerList(){
        List<PhotographerForListDto> photographerList = photographerService.getPhotographerListByUser();
        return new ResponseEntity<>(photographerList, HttpStatus.OK);
    }
}
