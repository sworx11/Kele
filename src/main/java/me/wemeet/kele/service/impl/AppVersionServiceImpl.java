package me.wemeet.kele.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.wemeet.kele.entity.AppVersion;
import me.wemeet.kele.mapper.AppVersionMapper;
import me.wemeet.kele.service.IAppVersionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Quino Wu
 * @since 2022-04-30
 */
@Service
public class AppVersionServiceImpl extends ServiceImpl<AppVersionMapper, AppVersion> implements IAppVersionService {

    @Override
    public void insertAppVersion(AppVersion appVersion) {
        save(appVersion);
    }

    @Override
    public AppVersion getLatestAppVersion() {
        List<AppVersion> list = listAllAppVersion();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<AppVersion> listAllAppVersion() {
        QueryWrapper<AppVersion> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .orderByDesc(AppVersion::getId);
        return list(wrapper);
    }

    @Override
    public boolean isLatestAppVersion(String hash) {
        AppVersion item = getLatestAppVersion();
        if (item == null || item.getFileHash() == null) return false;
        return item.getFileHash().equals(hash);
    }
}
