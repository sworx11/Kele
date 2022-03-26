package me.wemeet.kele.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.wemeet.kele.entity.Album;
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
public interface IAlbumService extends IService<Album> {
    IPage<Album> listByFavorite(Page<Album> page, long userId);
    Album insertOrUpdate(Album album);
    void doFavorite(long userId, Album album);
    void unDoFavorite(long userId, long albumId);
    void batchUnDoFavorite(long userId, List<Long> ids);
    Album view(Album album);
    void play(Album album);
}
