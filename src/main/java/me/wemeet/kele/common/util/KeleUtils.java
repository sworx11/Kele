package me.wemeet.kele.common.util;

public class KeleUtils {
    public static String generateVerificationCode() {
        return String.valueOf(Math.random() * 100000).replaceAll("\\.", "").substring(0, 4);
    }

    public static String generateVerificationContent(String code, String lang) {
        String contentZh = "[渴樂团队] 您的验证码是 " + code + " (有效期30分钟)";
        String contentEn = "[KELE team] your verification code is " + code + " (Expired in 30 minutes)";
        return "zh".equalsIgnoreCase(lang) ? contentZh : contentEn;
    }

    public static String nextNickName(String lang) {
        String random = String.valueOf(Math.random() * 100000).replaceAll("\\.", "").substring(0, 5);
        String nameZh = "渴樂用户_" + random;
        String nameEn = "KELE_" + random;
        return "zh".equalsIgnoreCase(lang) ? nameZh : nameEn;
    }
}
