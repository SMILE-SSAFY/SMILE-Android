package com.ssafy.api.controller;

import com.ssafy.api.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PayController {

    @Autowired
    private PayService payService;

    @GetMapping("/api/pay")
    public ResponseEntity<?> confirmPay(@RequestParam("receiptId") String receiptId){
        payService.confirmPay(receiptId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
