package com.ssafy.api.external;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.api.external.dto.component.S3Component;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * AWS S3 이미지 올리는 서비스
 *
 * @author 신민철
 */
@Component
@RequiredArgsConstructor
public class AWSS3UploadService implements UploadService {
    private final AmazonS3 amazonS3;
    private final S3Component s3Component;

    /***
     *
     * @param inputStream
     * @param objectMetadata
     * @param fileName
     *
     * awss3에 파일을 올리는 method
     */
    @Override
    public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName) {
        amazonS3.putObject(new PutObjectRequest(s3Component.getBucket(), fileName, inputStream, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }
}
