package com.ssafy.api.controller;


import com.ssafy.api.dto.PhotographerDto;
import com.ssafy.api.service.PhotographerService;
import com.ssafy.core.entity.Photographer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 작가 관련 Controller
 *
 * author @김정은
 *
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
     * @return 정상일 때 OK
     */
    @PostMapping
    public ResponseEntity<HttpStatus> registPhotographer(@RequestBody PhotographerDto photographer){
        photographerService.addPhotographer(photographer);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * 작가 프로필 조회
     *
     * @param idx
     * @return 작가 프로필 객체
     */
    @GetMapping("/{photographerId}")
    public ResponseEntity<PhotographerDto> getPhotographer(@PathVariable("photographerId") Long idx){
        return ResponseEntity.ok(photographerService.getPhotographer(idx));
    }

    /**
     * 작가 프로필 수정
     *
     * @param idx
     * @param photographer
     * @return 수정된 작가 프로필 객체
     */
    @PutMapping("/{photographerId}")
    // TODO: @RequestPart로 변경
    public ResponseEntity<PhotographerDto> changePhotographer(@PathVariable("photographerId") Long idx, @RequestBody PhotographerDto photographer){
        photographer.setPhotographerId(idx);
        return ResponseEntity.ok(photographerService.changePhotographer(photographer));
    }
}
