package me.wemeet.kele.controller;

import me.wemeet.kele.common.response.KeleResponseEntity;
import me.wemeet.kele.common.response.KeleResponseStatus;
import me.wemeet.kele.entity.User;
import me.wemeet.kele.service.CommonService;
import me.wemeet.kele.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Quino Wu
 * @since 2021-12-04
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private IUserService userService;

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    private CommonService commonService;

    @Autowired
    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }

    @GetMapping("verify/email")
    public KeleResponseEntity<Long> verifyEmail(String email) {
        long count = userService.countUserByEmail(email);
        return KeleResponseEntity.<Long>builder().ok(count).build();
    }

    @GetMapping("verify/phone")
    public KeleResponseEntity<Long> verifyPhone(String phone) {
        long count = userService.countUserByPhone(phone);
        return KeleResponseEntity.<Long>builder().ok(count).build();
    }

    @GetMapping("verify/name")
    public KeleResponseEntity<Long> verifyName(String name) {
        long count = userService.countUserByName(name);
        return KeleResponseEntity.<Long>builder().ok(count).build();
    }

    @PostMapping("register")
    public KeleResponseEntity<User> register(User user, @RequestParam(name = "signCode") String signCode, @RequestParam(name = "lang", defaultValue = "zh") String lang) {
        if (commonService.testSignCode(user.getEmail(), signCode)) {
            userService.createUser(user.getEmail(), user.getName(), user.getPassword(), lang);

            commonService.deleteSignCode(user.getEmail());
            return KeleResponseEntity.<User>builder().ok(user).build();
        } else {
            return KeleResponseEntity
                    .<User>builder()
                    .status(KeleResponseStatus.INSUFFICIENT_PERMISSION)
                    .build();
        }
    }

    @PostMapping("login")
    public KeleResponseEntity<User> login(User user) {
        user = userService.login(user);
        if (user != null) {
            String token = commonService.generateAccessToken(user.getId());
            Map<String, Object> ext = new HashMap<>();
            ext.put("token", token);
            return KeleResponseEntity.<User>builder().ok(user).ext(ext).build();
        } else {
            return KeleResponseEntity
                    .<User>builder()
                    .status(KeleResponseStatus.LOGIN_ERROR)
                    .build();
        }
    }

    @PostMapping("logout")
    public KeleResponseEntity<String> logout(User user) {
        commonService.deleteAccessToken(user.getId());
        return KeleResponseEntity.<String>builder().ok().build();
    }

    @PostMapping("password/reset")
    public KeleResponseEntity<?> passwordReset(User user, @RequestParam(name = "resetCode") String resetCode) {
        if (commonService.testResetCode(user.getEmail(), resetCode)) {
            userService.updatePassword(user.getId(), user.getPassword());

            commonService.deleteResetCode(user.getEmail());
            return KeleResponseEntity.builder().ok().build();
        } else {
            return KeleResponseEntity
                    .builder()
                    .status(KeleResponseStatus.INSUFFICIENT_PERMISSION)
                    .build();
        }
    }

    @PostMapping("nickName")
    public KeleResponseEntity<?> updateNickname(User user) {
        userService.updateNick(user.getId(), user.getNickName());
        return KeleResponseEntity.builder().ok().build();
    }

    @PostMapping("avatar")
    public KeleResponseEntity<?> updateAvatar(User user) {
        userService.updateAvatar(user.getId(), user.getAvatarUrl());
        return KeleResponseEntity.builder().ok().build();
    }

    @PostMapping("profile")
    public KeleResponseEntity<?> updateUserProfile(User user) {
        userService.updateById(user);
        return KeleResponseEntity.builder().ok().build();
    }
}
