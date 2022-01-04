package me.wemeet.kele.service;

import me.wemeet.kele.common.constant.RedisKeyConstant;
import me.wemeet.kele.common.helper.MailHelper;
import me.wemeet.kele.common.helper.RedisHelper;
import me.wemeet.kele.common.util.AesEncryptUtils;
import me.wemeet.kele.common.util.KeleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Locale;
import java.util.UUID;

@Component
public class CommonService {
    private static final long VERIFICATION_CODE_EXPIRE_TIME = 1800L;
    private static final long CODE_EXPIRE_TIME = 1800L;

    private MailHelper mailHelper;
    
    @Autowired
    public void setMailHelper(MailHelper mailHelper) {
        this.mailHelper = mailHelper;
    }

    private RedisHelper redisHelper;

    @Autowired
    public void setRedisHelper(RedisHelper redisHelper) {
        this.redisHelper = redisHelper;
    }
    
    @Value("${aes.decryptKey}")
    private String decryptKey;

    public String sendSignVerificationCode(String mailToBase64, String lang) {
        return sentVerificationCode(RedisKeyConstant.VERIFICATION_CODE_SIGN_UP, mailToBase64, lang);
    }

    public boolean testSignVerificationCode(String mailToBase64, String code) {
        String key = RedisKeyConstant.VERIFICATION_CODE_SIGN_UP + RedisKeyConstant.DELIMITER + mailToBase64;
        return redisHelper.hashKey(key) && code.equals(redisHelper.get(key));
    }

    public void deleteSignVerificationCode(String mailToBase64) {
        String key = RedisKeyConstant.VERIFICATION_CODE_SIGN_UP + RedisKeyConstant.DELIMITER + mailToBase64;
        redisHelper.del(key);
    }

    public String generateSignCode(String mailToBase64) {
        String code = UUID.randomUUID().toString();
        String key = RedisKeyConstant.SIGN_UP_CODE + RedisKeyConstant.DELIMITER + mailToBase64;
        redisHelper.set(key, code, CODE_EXPIRE_TIME);
        return code;
    }

    public boolean testSignCode(String mailToBase64, String code) {
        String key = RedisKeyConstant.SIGN_UP_CODE + RedisKeyConstant.DELIMITER + mailToBase64;
        return redisHelper.hashKey(key) && code.equals(redisHelper.get(key));
    }

    public void deleteSignCode(String mailToBase64) {
        redisHelper.del(RedisKeyConstant.SIGN_UP_CODE + RedisKeyConstant.DELIMITER + mailToBase64);
    }

    public String sendResetVerificationCode(String mailToBase64, String lang) {
        return sentVerificationCode(RedisKeyConstant.VERIFICATION_CODE_RESET, mailToBase64, lang);
    }

    public boolean testResetVerificationCode(String mailToBase64, String code) {
        String key = RedisKeyConstant.VERIFICATION_CODE_RESET + RedisKeyConstant.DELIMITER + mailToBase64;
        return redisHelper.hashKey(key) && code.equals(redisHelper.get(key));
    }

    public void deleteResetVerificationCode(String mailToBase64) {
        String key = RedisKeyConstant.VERIFICATION_CODE_RESET + RedisKeyConstant.DELIMITER + mailToBase64;
        redisHelper.del(key);
    }

    public String generateResetCode(String mailToBase64) {
        String code = UUID.randomUUID().toString();
        String key = RedisKeyConstant.RESET_CODE + RedisKeyConstant.DELIMITER + mailToBase64;
        redisHelper.set(key, code, CODE_EXPIRE_TIME);
        return code;
    }

    public boolean testResetCode(String mailToBase64, String code) {
        String key = RedisKeyConstant.RESET_CODE + RedisKeyConstant.DELIMITER + mailToBase64;
        return redisHelper.hashKey(key) && code.equals(redisHelper.get(key));
    }

    public void deleteResetCode(String mailToBase64) {
        redisHelper.del(RedisKeyConstant.RESET_CODE + RedisKeyConstant.DELIMITER + mailToBase64);
    }

    public String generateAccessToken(Long userId) {
        String token = userId + ":" + UUID.randomUUID().toString().toUpperCase(Locale.ROOT);
        try {
            token = AesEncryptUtils.encrypt(token, decryptKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        redisHelper.hset(RedisKeyConstant.ACCESS_TOKEN, String.valueOf(userId), token);
        return token;
    }

    public boolean testAccessToken(String token) {
        if (token == null || token.isBlank()) return false;
        try {
            String temp = AesEncryptUtils.decrypt(token, decryptKey);
            String userId = temp.split(":")[0];
            String _token = redisHelper.hget(RedisKeyConstant.ACCESS_TOKEN, userId).toString();
            return token.equals(_token);
        } catch (Exception e) {
            return false;
        }
    }

    public void deleteAccessToken(long userId) {
        if (redisHelper.hHasKey(RedisKeyConstant.ACCESS_TOKEN, String.valueOf(userId))) {
            redisHelper.hdel(RedisKeyConstant.ACCESS_TOKEN, String.valueOf(userId));
        }
    }

    private String sentVerificationCode(String type, String mailToBase64, String lang) {
        String code = KeleUtils.generateVerificationCode();
        String content = KeleUtils.generateVerificationContent(code, lang);

        String mailTo = new String(Base64.getDecoder().decode(mailToBase64), StandardCharsets.UTF_8);
        String subject = "zh".equalsIgnoreCase(lang) ? "验证码" : "Verification Code";
        try {
            mailHelper.sendMail(mailTo, subject, content, lang);
        } catch (Exception e) {
            return null;
        }

        if (redisHelper.set(type + RedisKeyConstant.DELIMITER + mailToBase64, code, VERIFICATION_CODE_EXPIRE_TIME)) {
            return code;
        }

        return null;
    }
}
