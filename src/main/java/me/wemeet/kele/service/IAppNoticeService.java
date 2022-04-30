package me.wemeet.kele.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.wemeet.kele.entity.AppNotice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Quino Wu
 * @since 2022-04-30
 */
public interface IAppNoticeService extends IService<AppNotice> {
    IPage<AppNotice> listAllNotice(Page<AppNotice> page);
    IPage<AppNotice> listNotice(Page<AppNotice> page);
}
