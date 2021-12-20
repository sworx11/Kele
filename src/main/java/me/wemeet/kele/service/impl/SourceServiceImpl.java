package me.wemeet.kele.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.wemeet.kele.entity.Source;
import me.wemeet.kele.mapper.SourceMapper;
import me.wemeet.kele.service.ISourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Quino Wu
 * @since 2021-12-04
 */
@Service
public class SourceServiceImpl extends ServiceImpl<SourceMapper, Source> implements ISourceService {
    @Override
    public List<Source> listAllOrderByWeight() {
        Wrapper<Source> wrapper = new QueryWrapper<Source>().eq("active", "Y").orderByDesc("weight");
        return list(wrapper);
    }

    @Override
    public String getCookieById(String id) {
        Wrapper<Source> wrapper = new QueryWrapper<Source>()
                .select("cookie")
                .eq("id", id)
                .eq("active", "Y");
        List<Source> sources = list(wrapper);
        if (sources == null || sources.size() < 1) {
            return "";
        }
        return sources.get(0).getCookie();
    }
}
