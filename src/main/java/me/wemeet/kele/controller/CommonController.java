package me.wemeet.kele.controller;

import me.wemeet.kele.common.response.KeleResponseEntity;
import me.wemeet.kele.common.response.KeleResponseStatus;
import me.wemeet.kele.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common")
public class CommonController {
    @Autowired
    private CommonService common;

    @GetMapping("verification/sign")
    public KeleResponseEntity<String> sendSignVerificationCode(String mailTo, String lang) {
        String code = common.sendSignVerificationCode(mailTo, lang);
        return KeleResponseEntity.ok(code);
    }

    @GetMapping("verification/sign/test")
    public KeleResponseEntity<String> testSignVerificationCode(String mailTo, String code) {
        boolean test = common.testSignVerificationCode(mailTo, code);
        if (test) {
            common.deleteSignVerificationCode(mailTo);
            return KeleResponseEntity.ok(common.generateSignCode(mailTo));
        }
        return new KeleResponseEntity<>(KeleResponseStatus.INSUFFICIENT_PERMISSION);
    }

    @GetMapping("verification/reset")
    public KeleResponseEntity<String> sendResetVerificationCode(String mailTo, String lang) {
        String code = common.sendResetVerificationCode(mailTo, lang);
        return KeleResponseEntity.ok(code);
    }

    @GetMapping("verification/reset/test")
    public KeleResponseEntity<String> testResetVerificationCode(String mailTo, String code) {
        boolean test = common.testResetVerificationCode(mailTo, code);
        if (test) {
            common.deleteResetVerificationCode(mailTo);
            return KeleResponseEntity.ok(common.generateResetCode(mailTo));
        }
        return new KeleResponseEntity<>(KeleResponseStatus.INSUFFICIENT_PERMISSION);
    }
}
