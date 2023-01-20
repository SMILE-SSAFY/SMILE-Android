package com.ssafy.api.controller;


import com.ssafy.api.config.security.jwt.JwtTokenProvider;
import com.ssafy.api.dto.ArticlePostDto;
import com.ssafy.api.dto.PhotographerDto;
import com.ssafy.api.service.PhotographerService;
import com.ssafy.api.service.S3UploaderService;
import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

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
    @Autowired
    private S3UploaderService s3UploaderService;

    /**
     * 작가 프로필 등록
     *
     * @param photographer
     * @param multipartFile
     * @return 정상일 때 OK
     */
    @PostMapping
    public ResponseEntity<HttpStatus> registPhotographer(@RequestPart("Photographer") PhotographerDto photographer,
                                                         @RequestPart("image") MultipartFile multipartFile) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();

        // 파일 업로드
        String fileName = s3UploaderService.upload(multipartFile);

        photographer.setPhotographerId(user.getId());
        photographer.setProfileImg(fileName);
        photographerService.addPhotographer(photographer);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * 작가 프로필 조회
     *
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


    /**
     * 작가 프로필 삭제
     *
     * @return 삭제 성공 시 OK
     */
    @DeleteMapping
    public ResponseEntity<HttpStatus> removePhotographer(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        photographerService.removePhotographer(user.getId());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
