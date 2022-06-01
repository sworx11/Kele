package me.wemeet.kele.service;

import me.wemeet.kele.entity.PlaylistFavorite;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Quino Wu
 * @since 2022-01-05
 */
public interface IPlaylistFavoriteService extends IService<PlaylistFavorite> {
    void batchFavorite(long userId, List<Long> ids);

}
