package com.ssafy.api.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ssafy.api.external.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
@Service
public class S3UploaderService {
    private final UploadService s3Service;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /***
     *
     * @param multipartFile
     * @return
     * @throws IOException
     *
     * 파일을 여러개 받아서 하나씩 사이즈, 이름 , 컨텐트타입을 비교하고 업로드 함
     */
    public String upload(List<MultipartFile> multipartFile) throws IOException {
        List<String> fileNameList = new ArrayList<>();

        multipartFile.forEach(file -> {
            String fileName = file.getOriginalFilename();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());
            String newFileName = createFileName(fileName);

            try (InputStream inputStream = file.getInputStream()) {
                s3Service.uploadFile(inputStream, objectMetadata, newFileName);
            } catch (IOException e) {
                throw new IllegalArgumentException(String.format("파일 변환 중 에러가 발생 했습니다 (%s).", file.getOriginalFilename()));
            }
            fileNameList.add(newFileName);
        });
        String fileNameString = fileNameList.toString();
        return fileNameString;
    }

    /***
     *
     * @param fileName
     *
     * s3에 업로드 되어 있는 파일 삭제
     */
    public void deleteFile(String fileName){
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    /***
     *
     * @param originalFileName
     * @return 새로운 파일네임 부여
     */
    private String createFileName(String originalFileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
    }

    /***
     *
     * @param fileName
     * @return 파일 형식 체크
     */
    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(String.format("잘못된 형식의 파일 (%s) 입니다", fileName));
        }
    }
}
