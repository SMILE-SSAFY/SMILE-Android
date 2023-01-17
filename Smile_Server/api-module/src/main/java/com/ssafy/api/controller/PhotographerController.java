package com.ssafy.api.controller;


import com.ssafy.api.dto.PhotographerDto;
import com.ssafy.api.service.PhotographerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/photographer")
public class PhotographerController {

    @Autowired
    private PhotographerService photographerService;

    @PostMapping
    public ResponseEntity<HttpStatus> registPhotographer(@RequestBody PhotographerDto photographer){
        photographerService.addPhotographer(photographer);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
