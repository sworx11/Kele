package me.wemeet.kele.controller;

import me.wemeet.kele.common.response.KeleResponseEntity;
import me.wemeet.kele.common.response.KeleResponseStatus;
import me.wemeet.kele.entity.*;
import me.wemeet.kele.entity.dto.Duplicate;
import me.wemeet.kele.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/duplicate")
public class DuplicateController {

    private IFavoriteService favoriteService;

    @Autowired
    public void setFavoriteService(IFavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    private ISongService songService;

    @Autowired
    public void setSongService(ISongService songService) {
        this.songService = songService;
    }

    private IPlaylistService playlistService;

    @Autowired
    public void setPlaylistService(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    private IPlaylistFavoriteService playlistFavoriteService;

    @Autowired
    public void setPlaylistFavoriteService(IPlaylistFavoriteService playlistFavoriteService) {
        this.playlistFavoriteService = playlistFavoriteService;
    }

    private IAlbumService albumService;

    @Autowired
    public void setAlbumService(IAlbumService albumService) {
        this.albumService = albumService;
    }

    private IAlbumFavoriteService albumFavoriteService;

    @Autowired
    public void setAlbumFavoriteService(IAlbumFavoriteService albumFavoriteService) {
        this.albumFavoriteService = albumFavoriteService;
    }

    @PostMapping("")
    public KeleResponseEntity<?> duplicate(@RequestParam(name = "userId") String userId, @RequestBody Duplicate duplicate) {

        if (userId == null || userId.isBlank()) {
            return KeleResponseEntity.builder().status(KeleResponseStatus.REQUEST_PARAMETER_ERROR).build();
        }

        long uid = Long.parseLong(userId);
        duplicateSongs(uid, duplicate.getSongs());
        duplicatePlaylist(uid, duplicate.getPlaylists());
        duplicateAlbum(uid, duplicate.getAlbums());

        return KeleResponseEntity.builder().ok().build();
    }

    @Async("duplicateExecutor")
    public void duplicateSongs(long uid, List<Song> songs) {
        if (songs == null || songs.size() < 1) return;

        List<Long> ids = new ArrayList<>();
        songs.forEach(song -> {
            song = songService.insertOrUpdate(song);
            ids.add(song.getId());
        });

        favoriteService.batchFavorite(uid, ids);
    }

    @Async("duplicateExecutor")
    public void duplicatePlaylist(long uid, List<Playlist> playlists) {
        if (playlists == null || playlists.size() < 1) return;

        List<Long> ids = new ArrayList<>();
        playlists.forEach(playlist -> {
            playlist = playlistService.insertOrUpdate(playlist);
            ids.add(playlist.getId());
        });

        playlistFavoriteService.batchFavorite(uid, ids);
    }

    @Async("duplicateExecutor")
    public void duplicateAlbum(long uid, List<Album> albums) {
        if (albums == null || albums.size() < 1) return;

        List<Long> ids = new ArrayList<>();
        albums.forEach(album -> {
            album = albumService.insertOrUpdate(album);
            ids.add(album.getId());
        });

        albumFavoriteService.batchFavorite(uid, ids);
    }
}
