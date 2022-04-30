package me.wemeet.kele.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.wemeet.kele.common.constant.KeleConstant;
import me.wemeet.kele.entity.AppNotice;
import me.wemeet.kele.mapper.AppNoticeMapper;
import me.wemeet.kele.service.IAppNoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Quino Wu
 * @since 2022-04-30
 */
@Service
public class AppNoticeServiceImpl extends ServiceImpl<AppNoticeMapper, AppNotice> implements IAppNoticeService {

    private AppNoticeMapper appNoticeMapper;

    @Autowired
    public void setAppNoticeMapper(AppNoticeMapper appNoticeMapper) {
        this.appNoticeMapper = appNoticeMapper;
    }

    @Override
    public IPage<AppNotice> listAllNotice(Page<AppNotice> page) {
        QueryWrapper<AppNotice> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .orderByDesc(AppNotice::getCreateAt);
        return appNoticeMapper.selectPage(page, wrapper);
    }

    @Override
    public IPage<AppNotice> listNotice(Page<AppNotice> page) {
        QueryWrapper<AppNotice> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(AppNotice::getActive, KeleConstant.ACTIVE)
                .orderByDesc(AppNotice::getCreateAt);
        return appNoticeMapper.selectPage(page, wrapper);
    }

}
