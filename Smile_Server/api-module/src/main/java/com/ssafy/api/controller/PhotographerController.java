package com.ssafy.api.controller;


import com.ssafy.api.config.security.jwt.JwtTokenProvider;
import com.ssafy.api.dto.PhotographerDto;
import com.ssafy.api.service.PhotographerService;
import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        photographer.setPhotographerId(user.getId());
        photographerService.addPhotographer(photographer);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * 작가 프로필 조회
     *
     * @param idx
     * @return 작가 프로필 객체
     */
    @GetMapping
    public ResponseEntity<PhotographerDto> getPhotographer(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        return ResponseEntity.ok(photographerService.getPhotographer(user.getId()));
    }

    /**
     * 작가 프로필 수정
     *
     * @param idx
     * @param photographer
     * @return 수정된 작가 프로필 객체
     */
    @PutMapping
    // TODO: @RequestPart로 변경
    public ResponseEntity<PhotographerDto> changePhotographer(@RequestBody PhotographerDto photographer){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        photographer.setPhotographerId(user.getId());
        return ResponseEntity.ok(photographerService.changePhotographer(photographer));
    }
}
