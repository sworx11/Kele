package me.wemeet.kele.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.wemeet.kele.entity.Favorite;
import me.wemeet.kele.entity.Song;
import me.wemeet.kele.mapper.FavoriteMapper;
import me.wemeet.kele.service.IFavoriteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements IFavoriteService {
    private FavoriteMapper favoriteMapper;

    @Autowired
    public void setFavoriteMapper(FavoriteMapper favoriteMapper) {
        this.favoriteMapper = favoriteMapper;
    }

    @Override
    public IPage<Song> listFavoriteByUser(Page<Song> page, long userId) {
        return favoriteMapper.listFavoriteByUser(page, userId);
    }

    @Override
    public List<Song> allFavoriteByUser(long userId) {
        return favoriteMapper.allFavoriteByUser(userId);
    }

    @Override
    public void insertOrUpdate(Favorite favorite) {
        int count = favoriteMapper.countByUserAndSong(favorite.getUserId(), favorite.getSongId());
        if (count > 0) return;
        favoriteMapper.insert(favorite);
    }

    @Override
    public void deleteByUserAndSong(long userId, long songId) {
        favoriteMapper.deleteByUserAndSong(userId, songId);
    }
}
