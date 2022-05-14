package me.wemeet.kele.controller.admin;

import me.wemeet.kele.common.response.KeleResponseEntity;
import me.wemeet.kele.entity.Config;
import me.wemeet.kele.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/config")
public class ConfigAdminController {
    private IConfigService configService;
    
    @Autowired
    public void setConfigService(IConfigService configService) {
        this.configService = configService;
    }

    @GetMapping("")
    public KeleResponseEntity<List<Config>> listConfig() {
        List<Config> list = configService.list();
        return KeleResponseEntity.<List<Config>>builder().ok(list).build();
    }

    @PostMapping("")
    public KeleResponseEntity<?> saveFile(@RequestBody Config config) {
        configService.saveOrUpdate(config);
        return KeleResponseEntity.builder().ok().build();
    }

    @DeleteMapping("")
    public KeleResponseEntity<?> deleteFile(@RequestBody Config config) {
        configService.removeById(config.getId());
        return KeleResponseEntity.builder().ok().build();
    }
}
