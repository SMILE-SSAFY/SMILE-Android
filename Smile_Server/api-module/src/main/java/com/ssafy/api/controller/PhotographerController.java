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

}
