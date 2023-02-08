package com.ssafy.batch.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * FCM 데이터 메시지 DTO
 *
 * @author 김정은
 *
 */
@Data
@NoArgsConstructor
public class FcmDataMessage {
    private boolean validate_only;
    private Message message;

	@Builder
    public FcmDataMessage(boolean validate_only, Message message) {
		super();
		this.validate_only = validate_only;
		this.message = message;
	}


	/** Message
	 * 
	 * @author 김정은
	 *
	 */
	@Data
	@NoArgsConstructor
	public static class Message {
        private Notification notification;
        private String token;
        private Map<String, String> data;

		@Builder
		public Message(Notification notification, String token, Map<String, String> data) {
			super();
			this.notification = notification;
			this.token = token;
			this.data = data;
		}

    }

	/** Notification
	 * 
	 * @author 김정은
	 *
	 */
	@Data
    public static class Notification {
        private String title;
        private String body;
        private String image;
        
        public Notification() {}

		@Builder
		public Notification(String title, String body, String image) {
			super();
			this.title = title;
			this.body = body;
			this.image = image;
		}
    }
}
