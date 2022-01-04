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

    private CommonService commonService;

    @Autowired
    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }

    @GetMapping("verification/sign")
    public KeleResponseEntity<String> sendSignVerificationCode(String mailTo, String lang) {
        String code = commonService.sendSignVerificationCode(mailTo, lang);
        return KeleResponseEntity.<String>builder().ok(code).build();
    }

    @GetMapping("verification/sign/test")
    public KeleResponseEntity<String> testSignVerificationCode(String mailTo, String code) {
        boolean test = commonService.testSignVerificationCode(mailTo, code);
        if (test) {
            commonService.deleteSignVerificationCode(mailTo);
            return KeleResponseEntity
                    .<String>builder()
                    .ok(commonService.generateSignCode(mailTo))
                    .build();
        }
        return KeleResponseEntity
                .<String>builder()
                .status(KeleResponseStatus.INSUFFICIENT_PERMISSION)
                .build();
    }

    @GetMapping("verification/reset")
    public KeleResponseEntity<String> sendResetVerificationCode(String mailTo, String lang) {
        String code = commonService.sendResetVerificationCode(mailTo, lang);
        return KeleResponseEntity.<String>builder().ok(code).build();
    }

    @GetMapping("verification/reset/test")
    public KeleResponseEntity<String> testResetVerificationCode(String mailTo, String code) {
        boolean test = commonService.testResetVerificationCode(mailTo, code);
        if (test) {
            commonService.deleteResetVerificationCode(mailTo);
            return KeleResponseEntity
                    .<String>builder()
                    .ok(commonService.generateResetCode(mailTo))
                    .build();
        }
        return KeleResponseEntity
                .<String>builder()
                .status(KeleResponseStatus.INSUFFICIENT_PERMISSION)
                .build();
    }
}
