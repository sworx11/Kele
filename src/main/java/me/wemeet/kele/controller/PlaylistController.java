package me.wemeet.kele.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.wemeet.kele.common.response.KeleResponseEntity;
import me.wemeet.kele.entity.Playlist;
import me.wemeet.kele.entity.Song;
import me.wemeet.kele.entity.dto.PlaylistDTO;
import me.wemeet.kele.service.IPlaylistService;
import me.wemeet.kele.service.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Quino Wu
 * @since 2021-12-04
 */
@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    private IPlaylistService playlistService;

    @Autowired
    public void setPlaylistService(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    private ISongService songService;

    @Autowired
    public void setSongService(ISongService songService) {
        this.songService = songService;
    }

    @GetMapping("")
    public KeleResponseEntity<IPage<Playlist>> listByUser(
            String userId,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        IPage<Playlist> result = playlistService.listByUser(new Page<>(page, limit), Long.parseLong(userId));
        return KeleResponseEntity.<IPage<Playlist>>builder().ok(result).build();
    }

    @GetMapping("favorite")
    public KeleResponseEntity<IPage<Playlist>> listByFavorite(
            String userId,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        IPage<Playlist> result = playlistService.listByFavorite(new Page<>(page, limit), Long.parseLong(userId));
        return KeleResponseEntity.<IPage<Playlist>>builder().ok(result).build();
    }

    @PostMapping("")
    public KeleResponseEntity<Playlist> insertOrUpdateKelePlaylist(@RequestBody PlaylistDTO playlistDTO) {
        Playlist playlist = playlistService.insertOrUpdateKelePlaylist(playlistDTO.getPlaylist());

        if (playlistDTO.getSongs() != null && playlistDTO.getSongs().size() > 0) {
            List<Song> songs = songService.batchInsertOrUpdateReturnSong(playlistDTO.getSongs());

            playlistService.addSongsToKelePlayList(playlist.getId(), songs.parallelStream().map(Song::getId).collect(Collectors.toList()));
        }

        return KeleResponseEntity.<Playlist>builder().ok(playlist).build();
    }

    @PostMapping("inactive")
    public KeleResponseEntity<?> inactiveKelePlaylist(String playlistId) {
        playlistService.inactiveKelePlaylist(Long.parseLong(playlistId));
        return KeleResponseEntity.builder().ok().build();
    }

    @PostMapping("favorite")
    public KeleResponseEntity<?> doFavorite(String userId, @RequestBody Playlist playlist) {
        playlistService.doFavorite(Long.parseLong(userId), playlist);
        return KeleResponseEntity.builder().ok().build();
    }

    @DeleteMapping("favorite")
    public KeleResponseEntity<?> unDoFavorite(String userId, String playlistId) {
        playlistService.unDoFavorite(Long.parseLong(userId), Long.parseLong(playlistId));
        return KeleResponseEntity.builder().ok().build();
    }

    @DeleteMapping("favorite/batch")
    public KeleResponseEntity<?> batchUnDoFavorite(String userId, @RequestBody List<String> ids) {
        playlistService.batchUnDoFavorite(Long.parseLong(userId), ids.parallelStream().map(Long::parseLong).collect(Collectors.toList()));
        return KeleResponseEntity.builder().ok().build();
    }

    @PostMapping("song")
    public KeleResponseEntity<?> addSongsToKelePlayList(String playlistId, @RequestBody List<String> ids) {
        playlistService.addSongsToKelePlayList(Long.parseLong(playlistId), ids.parallelStream().map(Long::parseLong).collect(Collectors.toList()));
        return KeleResponseEntity.builder().ok().build();
    }

    @DeleteMapping("song")
    public KeleResponseEntity<?> removeSongsFromKelePlayList(String playlistId, @RequestBody List<String> ids) {
        playlistService.removeSongsFromKelePlaylist(Long.parseLong(playlistId), ids.parallelStream().map(Long::parseLong).collect(Collectors.toList()));
        return KeleResponseEntity.builder().ok().build();
    }

    @GetMapping("song")
    public KeleResponseEntity<List<Song>> listSongsByPlaylist(String playlistId) {
        List<Song> songs = playlistService.listSongsByPlaylist(Long.parseLong(playlistId));
        return KeleResponseEntity.<List<Song>>builder().ok(songs).build();
    }

    @GetMapping("detail")
    public KeleResponseEntity<PlaylistDTO> playlistDetail(String playlistId) {
        PlaylistDTO dto = playlistService.playlistDetail(Long.parseLong(playlistId));
        return KeleResponseEntity.<PlaylistDTO>builder().ok(dto).build();
    }

    @PostMapping("view")
    public KeleResponseEntity<Playlist> view(@RequestBody Playlist playlist) {
        playlist = playlistService.view(playlist);
        return KeleResponseEntity.<Playlist>builder().ok(playlist).build();
    }

    @PostMapping("play")
    public KeleResponseEntity<?> play(@RequestBody Playlist playlist) {
        playlistService.play(playlist);
        return KeleResponseEntity.builder().ok().build();
    }

    @GetMapping("favored")
    public KeleResponseEntity<Boolean> isFavorite(String userId, String source, String mid) {
        Boolean result = playlistService.isFavorite(Long.parseLong(userId), source, mid);
        return KeleResponseEntity.<Boolean>builder().ok(result).build();
    }

    @PostMapping("cover")
    public KeleResponseEntity<?> updateCover(Playlist playlist) {
        playlistService.updateCover(playlist.getCreateBy(), playlist.getId(), playlist.getCoverUrl());
        return KeleResponseEntity.builder().ok().build();
    }
}
