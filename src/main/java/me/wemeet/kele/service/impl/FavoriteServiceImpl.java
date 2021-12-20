package me.wemeet.kele.service.impl;

import me.wemeet.kele.entity.Favorite;
import me.wemeet.kele.mapper.FavoriteMapper;
import me.wemeet.kele.service.IFavoriteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Quino Wu
 * @since 2021-12-04
 */
@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements IFavoriteService {

}
