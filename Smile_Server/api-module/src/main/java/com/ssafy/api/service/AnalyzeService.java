package com.ssafy.api.service;

import com.google.cloud.language.v1.*;

/**
 * 리뷰 키워드 추출 관련 Service
 *
 * @author 이민하
 */
public class AnalyzeService {
    /***
     * 리뷰 키워드 추출
     * @param reviewText
     * @return String
     */
    public String analyzeEntitiesText(String reviewText) {
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
