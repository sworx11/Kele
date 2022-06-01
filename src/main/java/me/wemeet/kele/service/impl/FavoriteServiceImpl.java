package me.wemeet.kele.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.wemeet.kele.entity.Favorite;
import me.wemeet.kele.entity.Song;
import me.wemeet.kele.mapper.FavoriteMapper;
import me.wemeet.kele.service.IFavoriteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Song> listFavoriteByUser(long userId) {
        return favoriteMapper.listFavoriteByUser(userId);
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

    @Override
    public void batchFavorite(long userId, List<Long> ids) {
        List<Favorite> favorites = selectSongIdsByUser(userId);
        List<Long> songs = favorites.stream().map(Favorite::getSongId).collect(Collectors.toList());
        favorites = ids.stream().filter(id -> !songs.contains(id)).map(id -> {
            Favorite entity = new Favorite();
            entity.setSongId(id);
            entity.setUserId(userId);
            return entity;
        }).collect(Collectors.toList());
        saveBatch(favorites);
    }

    @Override
    public void batchDelete(List<Favorite> favorites) {
        favorites.forEach(favorite -> deleteByUserAndSong(favorite.getUserId(), favorite.getSongId()));
    }

    public List<Favorite> selectSongIdsByUser(long userId) {
        QueryWrapper<Favorite> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(Favorite::getSongId).eq(Favorite::getUserId, userId);
        return list(wrapper);
    }
}
