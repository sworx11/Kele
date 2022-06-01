package me.wemeet.kele.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.wemeet.kele.entity.AlbumFavorite;
import me.wemeet.kele.mapper.AlbumFavoriteMapper;
import me.wemeet.kele.service.IAlbumFavoriteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Quino Wu
 * @since 2022-03-26
 */
@Service
public class AlbumFavoriteServiceImpl extends ServiceImpl<AlbumFavoriteMapper, AlbumFavorite> implements IAlbumFavoriteService {

    @Override
    public void batchFavorite(long userId, List<Long> ids) {
        List<AlbumFavorite> favorites = selectAlbumIdsByUser(userId);
        List<Long> albums = favorites.stream().map(AlbumFavorite::getAlbumId).collect(Collectors.toList());
        favorites = ids.stream().filter(id -> !albums.contains(id)).map(id -> {
            AlbumFavorite entity = new AlbumFavorite();
            entity.setAlbumId(id);
            entity.setUserId(userId);
            return entity;
        }).collect(Collectors.toList());
        saveBatch(favorites);
    }

    public List<AlbumFavorite> selectAlbumIdsByUser(long userId) {
        QueryWrapper<AlbumFavorite> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(AlbumFavorite::getAlbumId).eq(AlbumFavorite::getUserId, userId);
        return list(wrapper);
    }
}
