package com.ssafy.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.ssafy.api.dto.Reservation.FcmDataMessage;
import com.ssafy.api.dto.Reservation.NotificationDTO;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.http.HttpHeaders;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FCM 전송 class
 *
 * @author 김정은
 */
@Component
@Slf4j
public class NotificationService {

    public final ObjectMapper objectMapper;

    // fcm url
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/ssafy-smile/messages:send";

    public NotificationService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * FCM Access Token 발급
     *
     * @return
     * @throws IOException
     */
    private String getAccessToken() throws IOException {
        // 설정파일 위치
        String firebaseConfigPath = "firebase/firebase_service_key.json";

        // GoogleApi를 사용하기 위해 oAuth2를 이용해 인증한 대상을 나타내는객체
        GoogleCredentials googleCredentials = GoogleCredentials
                // 서버로부터 받은 service key 파일 활용
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                // 인증하는 서버에서 필요로 하는 권한 지정
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        String token = googleCredentials.getAccessToken().getTokenValue();

        return token;
    }

    /**
     * 전송될 데이터 타입 만들기
     *
     * @param targetToken
     * @param title
     * @param body
     * @return
     * @throws JsonProcessingException
     */
    private String makeDataMessage(String targetToken, String body) throws JsonProcessingException {
        Map<String,String> map = new HashMap<>();
        map.put("title", "smile");
        map.put("body", body);

        FcmDataMessage.Message message = FcmDataMessage.Message.builder()
                .token(targetToken)
                .data(map)
                .build();

        FcmDataMessage fcmMessage = new FcmDataMessage(false, message);

        return objectMapper.writeValueAsString(fcmMessage);
    }

    /**
     * 메세지 보내기
     *
     * @param notificationData
     * @throws IOException
     */
    public void sendDataMessageTo(NotificationDTO notificationData) throws IOException {
        String[] tokens = notificationData.getRegistrationToken().split(",");

        for(String token : tokens){
            String message = makeDataMessage(token, notificationData.getContent());
            log.info("message : {}", message);
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(API_URL)
                    .post(requestBody)
                    // 전송 토큰 추가
                    .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                    .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                    .build();

            Response response = client.newCall(request).execute();

            log.info(response.body().string());
        }
    }
}
