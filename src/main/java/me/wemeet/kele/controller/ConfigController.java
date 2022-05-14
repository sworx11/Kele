package me.wemeet.kele.controller;

import me.wemeet.kele.common.response.KeleResponseEntity;
import me.wemeet.kele.entity.Config;
import me.wemeet.kele.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Quino Wu
 * @since 2022-05-14
 */
@RestController
@RequestMapping("/config")
public class ConfigController {
    private IConfigService configService;

    @Autowired
    public void setConfigService(IConfigService configService) {
        this.configService = configService;
    }

    @GetMapping("/content")
    public KeleResponseEntity<Config> getContent(String key) {
        Config config = configService.getConfig(key);
        return KeleResponseEntity.<Config>builder().ok(config).build();
    }

    @GetMapping("/content/batch")
    public KeleResponseEntity<List<Config>> batchContent(String keys) {
        List<Config> list = configService.batchConfig(keys.split(","));
        return KeleResponseEntity.<List<Config>>builder().ok(list).build();
    }
}
