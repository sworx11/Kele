package me.wemeet.kele.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.wemeet.kele.entity.Favorite;
import com.baomidou.mybatisplus.extension.service.IService;
import me.wemeet.kele.entity.Song;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Quino Wu
 * @since 2021-12-04
 */
public interface IFavoriteService extends IService<Favorite> {
    IPage<Song> listFavoriteByUser(Page<Song> page, long userId);
    List<Song> allFavoriteByUser(long userId);
    void insertOrUpdate(Favorite favorite);
    void deleteByUserAndSong(long userId, long songId);
}
