package me.wemeet.kele.service;

import me.wemeet.kele.entity.Source;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Quino Wu
 * @since 2021-12-04
 */
public interface ISourceService extends IService<Source> {
    List<Source> listAllOrderByWeight();
    String getCookieById(String id);
}
