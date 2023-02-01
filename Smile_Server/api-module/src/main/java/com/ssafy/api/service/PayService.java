package com.ssafy.api.service;

import kr.co.bootpay.Bootpay;
import kr.co.bootpay.model.request.Cancel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * 결제 관련 Service Class
 *
 * @author 김정은
 */
@Service
@Slf4j
public class PayService {
    @Value("pay.rest-api")
    private String restApiKey;

    @Value("pay.private-key")
    private String privateKey;

    /**
     * 결제 승인
     *
     * @param receiptId
     */
    public void confirmPay(String receiptId){
        try {
            Bootpay bootpay = new Bootpay(restApiKey, privateKey);
            HashMap token = bootpay.getAccessToken();
            if(token.get("error_code") != null) { //failed
                return;
            }

            HashMap res = bootpay.confirm(receiptId);
            if(res.get("error_code") == null) { //success
                log.info("confirm success: " + res);
            } else {
                log.info("confirm false: " + res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 결제 취소
     *
     * @param receiptId
     */
    public void cancelPay(String receiptId){
        try {
            Bootpay bootpay = new Bootpay(restApiKey, privateKey);
            HashMap token = bootpay.getAccessToken();
            if(token.get("error_code") != null) { //failed
                return;
            }
            Cancel cancel = new Cancel();
            cancel.receiptId = receiptId;
            cancel.cancelUsername = "관리자";
            cancel.cancelMessage = "테스트 결제";

            HashMap res = bootpay.receiptCancel(cancel);
            if(res.get("error_code") == null) { //success
                log.info("receiptCancel success: " + res);
            } else {
                log.info("receiptCancel false: " + res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
