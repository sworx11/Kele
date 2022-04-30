package me.wemeet.kele.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.wemeet.kele.common.response.KeleResponseEntity;
import me.wemeet.kele.entity.AppNotice;
import me.wemeet.kele.service.IAppNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Quino Wu
 * @since 2022-04-30
 */
@RestController
@RequestMapping("/app-notice")
public class AppNoticeController {
    private IAppNoticeService appNoticeService;

    @Autowired
    public void setAppNoticeService(IAppNoticeService appNoticeService) {
        this.appNoticeService = appNoticeService;
    }

    @GetMapping("")
    public KeleResponseEntity<IPage<AppNotice>> listAppNotice(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "limit", required = false, defaultValue = "20") int limit
    ) {
        IPage<AppNotice> result = appNoticeService.listNotice(new Page<>(page, limit));
        return KeleResponseEntity.<IPage<AppNotice>>builder().ok(result).build();
    }
}
