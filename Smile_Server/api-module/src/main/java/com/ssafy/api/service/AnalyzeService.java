package com.ssafy.api.service;

import com.google.cloud.language.v1.AnalyzeEntitiesRequest;
import com.google.cloud.language.v1.AnalyzeEntitiesResponse;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.EncodingType;
import com.google.cloud.language.v1.Entity;
import com.google.cloud.language.v1.LanguageServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 리뷰 키워드 추출 관련 Service
 *
 * @author 이민하
 */
@Service
@Slf4j
public class AnalyzeService {
    /***
     * 리뷰 키워드 추출
     * @param reviewText
     * @return String
     */
    public String analyzeEntitiesText(String reviewText) {
        reviewText = reviewText.replace("\\n", " ");

        StringBuilder res = new StringBuilder();

        try (LanguageServiceClient language = LanguageServiceClient.create()) {
            Document doc = Document.newBuilder().setContent(reviewText).setType(Document.Type.PLAIN_TEXT).build();
            AnalyzeEntitiesRequest request =
                    AnalyzeEntitiesRequest.newBuilder()
                            .setDocument(doc)
                            .setEncodingType(EncodingType.UTF16)
                            .build();

            AnalyzeEntitiesResponse response = language.analyzeEntities(request);

            for (Entity entity : response.getEntitiesList()) {
                res.append(entity.getName()).append(" ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return res.toString();
    }
}
