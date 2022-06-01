package me.wemeet.kele.service;

import me.wemeet.kele.entity.AlbumFavorite;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Quino Wu
 * @since 2022-03-26
 */
public interface IAlbumFavoriteService extends IService<AlbumFavorite> {
    void batchFavorite(long userId, List<Long> ids);
}
