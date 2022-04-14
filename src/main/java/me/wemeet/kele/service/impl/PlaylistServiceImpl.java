package me.wemeet.kele.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.wemeet.kele.common.constant.KeleConstant;
import me.wemeet.kele.entity.*;
import me.wemeet.kele.entity.dto.PlaylistDTO;
import me.wemeet.kele.mapper.*;
import me.wemeet.kele.service.IPlaylistService;
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
public class PlaylistServiceImpl extends ServiceImpl<PlaylistMapper, Playlist> implements IPlaylistService {

    private PlaylistMapper playlistMapper;

    @Autowired
    public void setPlaylistMapper(PlaylistMapper playlistMapper) {
        this.playlistMapper = playlistMapper;
    }

    private PlaylistExtendsMapper playlistExtendsMapper;

    @Autowired
    public void setPlaylistMapper(PlaylistExtendsMapper playlistExtendsMapper) {
        this.playlistExtendsMapper = playlistExtendsMapper;
    }

    private PlaylistFavoriteMapper playlistFavoriteMapper;

    @Autowired
    public void setPlaylistFavoriteMapper(PlaylistFavoriteMapper playlistFavoriteMapper) {
        this.playlistFavoriteMapper = playlistFavoriteMapper;
    }

    private PlaylistSongMapper playlistSongMapper;

    @Autowired
    public void setPlaylistSongMapper(PlaylistSongMapper playlistSongMapper) {
        this.playlistSongMapper = playlistSongMapper;
    }

    @Override
    public IPage<Playlist> listByUser(Page<Playlist> page, long userId) {
        QueryWrapper<Playlist> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Playlist::getActive, KeleConstant.ACTIVE)
                .eq(Playlist::getSource, "kl")
                .eq(Playlist::getCreateBy, userId)
                .orderByDesc(Playlist::getCreateAt);

        return playlistMapper.selectPage(page, wrapper);
    }

    @Override
    public IPage<Playlist> listByFavorite(Page<Playlist> page, long userId) {
        return playlistMapper.listByFavorite(page, userId);
    }

    @Override
    public Playlist insertOrUpdateKelePlaylist(Playlist playlist) {
        boolean isNew = playlist.getId() == null;
        saveOrUpdate(playlist);
        if (isNew) {
            PlaylistExtends playlistExtends = new PlaylistExtends();
            playlistExtends.setPlaylistId(playlist.getId());
            playlistExtends.setFavorites(0);
            playlistExtends.setViews(0);
            playlistExtends.setPlays(0);
            playlistExtendsMapper.insert(playlistExtends);
        }
        return playlist;
    }

    @Override
    public void inactiveKelePlaylist(long playlistId) {
        Playlist playlist = getById(playlistId);
        playlist.setActive(KeleConstant.INACTIVE);
        updateById(playlist);
    }

    @Override
    public Playlist insertOrUpdate(Playlist playlist) {
        QueryWrapper<Playlist> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Playlist::getMid, playlist.getMid())
                .eq(Playlist::getSource, playlist.getSource());

        Playlist _playlist = playlistMapper.selectOne(wrapper);

        if (_playlist == null) {
            save(playlist);
        } else {
            playlist.setId(_playlist.getId());
            updateById(playlist);
        }

        return playlist;
    }

    @Override
    public void doFavorite(long userId, Playlist playlist) {
        if (playlist == null) return;

        if (playlist.getId() == null || playlist.getId() == 0 && !playlist.getSource().equals("kl")) {
            playlist = insertOrUpdate(playlist);
        }

        if (playlist != null && (playlist.getCreateBy() == null || playlist.getCreateBy() != userId)) {
            QueryWrapper<PlaylistFavorite> wrapper = new QueryWrapper<>();
            wrapper.lambda()
                    .eq(PlaylistFavorite::getUserId, userId)
                    .eq(PlaylistFavorite::getPlaylistId, playlist.getId());

            PlaylistFavorite favorite = playlistFavoriteMapper.selectOne(wrapper);
            if (favorite == null) {
                favorite = new PlaylistFavorite();
                favorite.setPlaylistId(playlist.getId());
                favorite.setUserId(userId);
                playlistFavoriteMapper.insert(favorite);
                initPlaylistExtends(playlist.getId(), KeleConstant.FAVORITE);
            }
        }
    }

    @Override
    public void unDoFavorite(long userId, long playlistId) {
        QueryWrapper<PlaylistFavorite> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(PlaylistFavorite::getPlaylistId, playlistId)
                .eq(PlaylistFavorite::getUserId, userId);

        playlistFavoriteMapper.delete(wrapper);
    }

    @Override
    public void batchUnDoFavorite(long userId, List<Long> ids) {
        QueryWrapper<PlaylistFavorite> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(PlaylistFavorite::getUserId, userId)
                .in(PlaylistFavorite::getPlaylistId, ids);
        playlistFavoriteMapper.delete(wrapper);
    }

    private long songInPlaylist(long songId, long playlistId) {
        QueryWrapper<PlaylistSong> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(PlaylistSong::getPlaylistId, playlistId)
                .eq(PlaylistSong::getSongId, songId);
        return playlistSongMapper.selectCount(wrapper);
    }

    @Override
    public void addSongsToKelePlayList(long playlistId, List<Long> ids) {
        ids.forEach(id -> {
            if (songInPlaylist(id, playlistId) == 0L) {
                PlaylistSong playlistSong = new PlaylistSong();
                playlistSong.setPlaylistId(playlistId);
                playlistSong.setSongId(id);
                playlistSongMapper.insert(playlistSong);
            }
        });
    }

    @Override
    public void removeSongsFromKelePlaylist(long playlistId, List<Long> ids) {
        QueryWrapper<PlaylistSong> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PlaylistSong::getPlaylistId, playlistId).in(PlaylistSong::getSongId, ids);
        playlistSongMapper.delete(wrapper);
    }

    @Override
    public List<Song> listSongsByPlaylist(long playlistId) {
        return playlistMapper.listSongsByPlaylist(playlistId);
    }

    @Override
    public PlaylistDTO playlistDetail(long playlistId) {
        Playlist playlist = getById(playlistId);
        if (playlist == null) return null;

        User user = playlistMapper.getUser(playlist.getCreateBy());

        List<Song> songs = playlistMapper.listSongsByPlaylist(playlistId);

        QueryWrapper<PlaylistExtends> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PlaylistExtends::getPlaylistId, playlistId);
        PlaylistExtends ext = playlistExtendsMapper.selectOne(wrapper);


        PlaylistDTO dto = new PlaylistDTO();
        dto.setPlaylist(playlist);
        dto.setSongs(songs);
        dto.setExt(ext);
        dto.setUser(user);
        return dto;
    }

    @Override
    public Playlist view(Playlist playlist) {
        if (playlist == null) return new Playlist();

        if (playlist.getId() == null || playlist.getId() == 0) {
            playlist = insertOrUpdate(playlist);
        }

        initPlaylistExtends(playlist.getId(), KeleConstant.VIEW);
        return playlist;
    }

    @Override
    public void play(Playlist playlist) {
        if (playlist == null) return;

        if (playlist.getId() == null || playlist.getId() == 0) {
            playlist = insertOrUpdate(playlist);
        }

        initPlaylistExtends(playlist.getId(), KeleConstant.PLAY);
    }

    @Override
    public boolean isFavorite(long userId, String source, String mid) {
        long count;

        if (source.equals("kl")) {
            count = playlistMapper.countKlFavorite(userId, Long.parseLong(mid));
        } else {
            count = playlistMapper.countFavorite(userId, source, mid);
        }

        return count > 0L;
    }

    private void initPlaylistExtends(long playlistId, String type) {
        QueryWrapper<PlaylistExtends> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PlaylistExtends::getPlaylistId, playlistId);
        PlaylistExtends ext = playlistExtendsMapper.selectOne(wrapper);

        if (ext == null) {
            ext = new PlaylistExtends();
            ext.setPlaylistId(playlistId);
            ext.setFavorites(type.equals(KeleConstant.FAVORITE) ? 1 : 0);
            ext.setViews(type.equals(KeleConstant.VIEW) ? 1 : 0);
            ext.setPlays(type.equals(KeleConstant.PLAY) ? 1 : 0);
            playlistExtendsMapper.insert(ext);
        } else {
            switch (type) {
                case KeleConstant.FAVORITE:
                    ext.setFavorites(ext.getFavorites() + 1);
                    break;
                case KeleConstant.VIEW:
                    ext.setViews(ext.getViews() + 1);
                    break;
                case KeleConstant.PLAY:
                    ext.setPlays(ext.getPlays() + 1);
                    break;
                default:
                    break;
            }
            playlistExtendsMapper.updateById(ext);
        }
    }
}
