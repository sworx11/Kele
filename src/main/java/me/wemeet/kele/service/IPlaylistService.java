package me.wemeet.kele.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.wemeet.kele.entity.Playlist;
import com.baomidou.mybatisplus.extension.service.IService;
import me.wemeet.kele.entity.Song;
import me.wemeet.kele.entity.dto.PlaylistDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Quino Wu
 * @since 2021-12-04
 */
public interface IPlaylistService extends IService<Playlist> {
    IPage<Playlist> listByUser(Page<Playlist> page, long userId);
    IPage<Playlist> listByFavorite(Page<Playlist> page, long userId);
    Playlist insertOrUpdateKelePlaylist(Playlist playlist);
    void inactiveKelePlaylist(long playlistId);
    Playlist insertOrUpdate(Playlist playlist);
    void doFavorite(long userId, Playlist playlist);
    void unDoFavorite(long userId, long playlistId);
    void batchUnDoFavorite(long userId, List<Long> ids);
    void addSongsToKelePlayList(long playlistId, List<Long> ids);
    void removeSongsFromKelePlaylist(long playlistId, List<Long> ids);
    List<Song> listSongsByPlaylist(long playlistId);
    PlaylistDTO playlistDetail(long playlistId);
    void viewPlaylist(long playlistId);
    void playPlaylist(long playlistId);
}
