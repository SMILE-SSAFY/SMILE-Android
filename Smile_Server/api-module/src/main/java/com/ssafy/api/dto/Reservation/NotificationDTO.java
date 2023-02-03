package com.ssafy.api.dto.Reservation;

import lombok.Builder;
import lombok.Data;

/**
 * 알림 메시지에 사용될 DTO
 *
 * @author 김정은
 */
@Data
@Builder
public class NotificationDTO {
    private long requestId;
    private String registrationToken;
    private String content;
}
