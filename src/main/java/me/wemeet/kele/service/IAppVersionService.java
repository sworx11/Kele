package me.wemeet.kele.service;

import me.wemeet.kele.entity.AppVersion;
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
public interface IAppVersionService extends IService<AppVersion> {
    void insertAppVersion(AppVersion appVersion);
    AppVersion getLatestAppVersion();
    List<AppVersion> listAllAppVersion();
    boolean isLatestAppVersion(String hash);
}
