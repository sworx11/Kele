package me.wemeet.kele.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.wemeet.kele.entity.PlaylistFavorite;
import me.wemeet.kele.mapper.PlaylistFavoriteMapper;
import me.wemeet.kele.service.IPlaylistFavoriteService;
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
 * @since 2022-01-05
 */
@Service
public class PlaylistFavoriteServiceImpl extends ServiceImpl<PlaylistFavoriteMapper, PlaylistFavorite> implements IPlaylistFavoriteService {

    @Override
    public void batchFavorite(long userId, List<Long> ids) {
        List<PlaylistFavorite> favorites = selectPlaylistIdsByUser(userId);
        List<Long> playlists = favorites.stream().map(PlaylistFavorite::getPlaylistId).collect(Collectors.toList());
        favorites = ids.stream().filter(id -> !playlists.contains(id)).map(id -> {
            PlaylistFavorite entity = new PlaylistFavorite();
            entity.setPlaylistId(id);
            entity.setUserId(userId);
            return entity;
        }).collect(Collectors.toList());
        saveBatch(favorites);
    }

    public List<PlaylistFavorite> selectPlaylistIdsByUser(long userId) {
        QueryWrapper<PlaylistFavorite> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(PlaylistFavorite::getPlaylistId).eq(PlaylistFavorite::getUserId, userId);
        return list(wrapper);
    }
}
