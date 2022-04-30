package me.wemeet.kele.controller;

import me.wemeet.kele.common.response.KeleResponseEntity;
import me.wemeet.kele.entity.AppVersion;
import me.wemeet.kele.service.IAppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Quino Wu
 * @since 2022-04-30
 */
@RestController
@RequestMapping("/app-version")
public class AppVersionController {
    private IAppVersionService appVersionService;

    @Autowired
    public void setAppVersionService(IAppVersionService appVersionService) {
        this.appVersionService = appVersionService;
    }

    @GetMapping("")
    public KeleResponseEntity<List<AppVersion>> listAllAppVersion() {
        List<AppVersion> list = appVersionService.listAllAppVersion();
        return KeleResponseEntity.<List<AppVersion>>builder().ok(list).build();
    }

    @GetMapping("/latest")
    public KeleResponseEntity<AppVersion> latestAppVersion() {
        AppVersion appVersion = appVersionService.getLatestAppVersion();
        return KeleResponseEntity.<AppVersion>builder().ok(appVersion).build();
    }
}
