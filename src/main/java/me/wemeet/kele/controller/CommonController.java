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
        return KeleResponseEntity.<String>builder().ok(code).build();
    }

    @GetMapping("verification/sign/test")
    public KeleResponseEntity<String> testSignVerificationCode(String mailTo, String code) {
        boolean test = common.testSignVerificationCode(mailTo, code);
        if (test) {
            common.deleteSignVerificationCode(mailTo);
            return KeleResponseEntity
                    .<String>builder()
                    .ok(common.generateSignCode(mailTo))
                    .build();
        }
        return KeleResponseEntity
                .<String>builder()
                .status(KeleResponseStatus.INSUFFICIENT_PERMISSION)
                .build();
    }

    @GetMapping("verification/reset")
    public KeleResponseEntity<String> sendResetVerificationCode(String mailTo, String lang) {
        String code = common.sendResetVerificationCode(mailTo, lang);
        return KeleResponseEntity.<String>builder().ok(code).build();
    }

    @GetMapping("verification/reset/test")
    public KeleResponseEntity<String> testResetVerificationCode(String mailTo, String code) {
        boolean test = common.testResetVerificationCode(mailTo, code);
        if (test) {
            common.deleteResetVerificationCode(mailTo);
            return KeleResponseEntity
                    .<String>builder()
                    .ok(common.generateResetCode(mailTo))
                    .build();
        }
        return KeleResponseEntity
                .<String>builder()
                .status(KeleResponseStatus.INSUFFICIENT_PERMISSION)
                .build();
    }
}
