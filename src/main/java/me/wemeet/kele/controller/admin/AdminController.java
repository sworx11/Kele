package me.wemeet.kele.controller.admin;

import me.wemeet.kele.common.response.KeleResponseEntity;
import me.wemeet.kele.common.response.KeleResponseStatus;
import me.wemeet.kele.entity.Admin;
import me.wemeet.kele.service.CommonService;
import me.wemeet.kele.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Quino Wu
 * @since 2022-04-30
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    private IAdminService adminService;

    @Autowired
    public void setUserService(IAdminService adminService) {
        this.adminService = adminService;
    }

    private CommonService commonService;

    @Autowired
    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }

    @PostMapping("login")
    public KeleResponseEntity<Admin> login(@RequestBody Admin admin) {
        admin = adminService.login(admin);
        if (admin != null) {
            String token = commonService.generateAdminAccessToken(admin.getId());
            Map<String, Object> ext = new HashMap<>();
            ext.put("token", token);
            return KeleResponseEntity.<Admin>builder().ok(admin).ext(ext).build();
        } else {
            return KeleResponseEntity
                    .<Admin>builder()
                    .status(KeleResponseStatus.LOGIN_ERROR)
                    .build();
        }
    }

    @PostMapping("logout")
    public KeleResponseEntity<?> logout(@RequestBody Admin admin) {
        commonService.deleteAdminAccessToken(admin.getId());
        return KeleResponseEntity.builder().ok().build();
    }
}
