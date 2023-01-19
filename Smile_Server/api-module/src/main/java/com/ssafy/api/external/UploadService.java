package com.ssafy.api.external;

import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.InputStream;

public interface UploadService {
    /***
     *
     * @param inputStream
     * @param objectMetadata
     * @param fileName
     *
     * 꼭 aws가 아닌 다른 서비스여도 가능하도록 uploadService 분리
     */
    void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName);

    String getFileUrl(String fileName);
}
