package me.wemeet.kele.service;

import me.wemeet.kele.entity.Config;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Quino Wu
 * @since 2022-05-14
 */
public interface IConfigService extends IService<Config> {
    Config getConfig(String key);
    List<Config> batchConfig(String[] keys);
}
