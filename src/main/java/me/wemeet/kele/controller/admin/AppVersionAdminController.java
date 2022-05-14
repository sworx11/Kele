package me.wemeet.kele.controller.admin;

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
@RequestMapping("/admin/app-version")
public class AppVersionAdminController {
    private IAppVersionService appVersionService;

    @Autowired
    public void setAppVersionService(IAppVersionService appVersionService) {
        this.appVersionService = appVersionService;
    }

    @PostMapping("")
    public KeleResponseEntity<?> saveAppVersion(@RequestBody AppVersion appVersion) {
        appVersionService.saveOrUpdate(appVersion);
        return KeleResponseEntity.builder().ok().build();
    }

    @GetMapping("")
    public KeleResponseEntity<List<AppVersion>> listAllAppVersion() {
        List<AppVersion> list = appVersionService.listAllAppVersion();
        return KeleResponseEntity.<List<AppVersion>>builder().ok(list).build();
    }

    @DeleteMapping("")
    public KeleResponseEntity<?> deleteFile(@RequestBody AppVersion appVersion) {
        appVersionService.removeById(appVersion.getId());
        return KeleResponseEntity.builder().ok().build();
    }
}
