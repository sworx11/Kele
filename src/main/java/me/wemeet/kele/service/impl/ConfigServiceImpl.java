package me.wemeet.kele.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.wemeet.kele.entity.Config;
import me.wemeet.kele.mapper.ConfigMapper;
import me.wemeet.kele.service.IConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Quino Wu
 * @since 2022-05-14
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {

    @Override
    public Config getConfig(String key) {
        QueryWrapper<Config> wrapper = new QueryWrapper<>();
        wrapper
                .lambda()
                .select(Config::getKey)
                .select(Config::getContent)
                .eq(Config::getKey, key);
        return getOne(wrapper);
    }

    @Override
    public List<Config> batchConfig(String[] keys) {
        QueryWrapper<Config> wrapper = new QueryWrapper<>();
        wrapper
                .lambda()
                .select(Config::getKey)
                .select(Config::getContent)
                .in(Config::getKey, List.of(keys));
        return list(wrapper);
    }
}
