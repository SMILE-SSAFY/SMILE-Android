package com.ssafy.api.controller;

import com.ssafy.api.dto.ArticlePostDto;
import com.ssafy.api.service.ArticleService;
import com.ssafy.api.service.S3UploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FileUploadController {
    @Autowired
    private ArticleService articleService;
    private final S3UploaderService s3UploaderService;

    @PostMapping("/article")
    public Long uploadImage(@RequestPart(value="ArticlePostReq") ArticlePostDto articlePostDto,
                            @RequestPart(value="image") List<MultipartFile> multipartFile) throws IOException {
        String fileName = s3UploaderService.upload(multipartFile);
        System.out.println(fileName);
        return articleService.postArticle(fileName, articlePostDto);
    }

}