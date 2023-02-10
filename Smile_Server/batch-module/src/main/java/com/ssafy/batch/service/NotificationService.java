package com.ssafy.batch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.ssafy.batch.dto.FcmDataMessage;
import com.ssafy.batch.dto.NotificationDTO;
import com.ssafy.core.code.ReservationStatus;
import com.ssafy.core.entity.Reservation;
import com.ssafy.core.entity.User;
import com.ssafy.core.repository.reservation.ReservationRepository;
import kr.co.bootpay.Bootpay;
import kr.co.bootpay.model.request.Cancel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * FCM 전송 class
 *
 * @author 김정은
 * @author 서재건
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class NotificationService {

    public final ObjectMapper objectMapper;

    @Value("${pay.rest-api}")
    private String restApiKey;

    @Value("${pay.private-key}")
    private String privateKey;

    private final ReservationRepository reservationRepository;

    // fcm url
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/ssafy-smile/messages:send";

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
        GoogleCredentials googleCredentials = null;
        googleCredentials = GoogleCredentials
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
     * @param body
     * @return message
     * @throws JsonProcessingException
     */
    private String makeDataMessage(String targetToken, String body) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
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

        for (String token : tokens) {
            String message = null;
            message = makeDataMessage(token, notificationData.getContent());

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

    /**
     * 예약 취소
     *
     * @param reservation
     * @throws Exception
     */
    public void cancelReservation(Reservation reservation) throws Exception {
        String token, name;
        User user = reservation.getUser();  // 예약한 유저
        User photographer = reservation.getPhotographer().getUser();    // 예약된 사진작가
        token = photographer.getFcmToken();  // 사진작가에게 전달
        name = user.getName();     // 유저이름으로 취소

        cancelPay(reservation.getReceiptId(), name);

        reservation.updateStatus(ReservationStatus.예약취소);
        log.info("예약 상태 : {}", reservation.getStatus());

        reservationRepository.save(reservation);

        // FCM 전송
        sendDataMessageTo(NotificationDTO.builder()
                .requestId(user.getId())
                .registrationToken(token)
                .content(reservation.getReservedAt() + "의 예약이 취소되었습니다.")
                .build());
    }

    /**
     * 결제취소
     *
     * @param receiptId 결제 영수증 번호
     * @param userName 유저 이름
     * @throws Exception
     */
    public void cancelPay(String receiptId, String userName) throws Exception {
        Bootpay bootpay = new Bootpay(restApiKey, privateKey);
        HashMap<String, Object> token = bootpay.getAccessToken();
        if (token.get("error_code") != null) { //failed
            return;
        }
        Cancel cancel = new Cancel();
        cancel.receiptId = receiptId;
        cancel.cancelUsername = userName;
        cancel.cancelMessage = "사용자 단순 변심";

        HashMap<String, Object> res = bootpay.receiptCancel(cancel);
        if (res.get("error_code") == null) { //success
            log.info("receiptCancel success: " + res);
        } else {
            log.error("receiptCancel false: " + res);
        }
    }
}
