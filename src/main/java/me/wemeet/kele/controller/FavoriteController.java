package me.wemeet.kele.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.wemeet.kele.common.response.KeleResponseEntity;
import me.wemeet.kele.entity.Favorite;
import me.wemeet.kele.entity.Song;
import me.wemeet.kele.service.IFavoriteService;
import me.wemeet.kele.service.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RequestMapping("/favorite")
public class FavoriteController {

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

    @GetMapping("")
    public KeleResponseEntity<IPage<Song>> listFavoriteByUser(
            String userId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "20") int size) {
        IPage<Song> result = favoriteService.listFavoriteByUser(new Page<>(page, size), Long.parseLong(userId));
        return KeleResponseEntity.<IPage<Song>>builder().ok(result).build();
    }

    @GetMapping("all")
    public KeleResponseEntity<List<Song>> allFavoriteByUser(String userId) {
        List<Song> result = favoriteService.allFavoriteByUser(Long.parseLong(userId));
        return KeleResponseEntity.<List<Song>>builder().ok(result).build();
    }

    @PostMapping("")
    public KeleResponseEntity<String> doFavorite(@RequestParam(name = "userId") String userId, @RequestBody Song song) {
        song = songService.insertOrUpdate(song);
        Favorite favorite = new Favorite();
        favorite.setSongId(song.getId());
        favorite.setUserId(Long.parseLong(userId));
        favoriteService.insertOrUpdate(favorite);
        return KeleResponseEntity.<String>builder().ok(String.valueOf(song.getId())).build();
    }

    @PostMapping("/batch")
    public KeleResponseEntity<?> batchDoFavorite(@RequestParam(name = "userId") String userId, @RequestBody List<Song> songs) {
        List<Long> ids = new ArrayList<>();
        songs.forEach(song -> {
            song = songService.insertOrUpdate(song);
            ids.add(song.getId());
        });

        long uid = Long.parseLong(userId);
        List<Favorite> favorites = ids.stream().map(id -> {
            Favorite f = new Favorite();
            f.setUserId(uid);
            f.setSongId(id);
            return f;
        }).collect(Collectors.toList());
        favoriteService.batchInsertOrUpdate(favorites);
        return KeleResponseEntity.builder().ok().build();
    }

    @DeleteMapping("")
    public KeleResponseEntity<?> undoFavorite(String userId, String songmid, String source) {
        Song song = songService.getBySongmidAndSource(songmid, source);
        favoriteService.deleteByUserAndSong(Long.parseLong(userId), song.getId());
        return KeleResponseEntity.builder().ok().build();
    }

    @DeleteMapping("/batch")
    public KeleResponseEntity<?> batchUndoFavorite(String userId, @RequestBody List<Song> songs) {
        long uid = Long.parseLong(userId);
        List<Favorite> favorites = songs.stream().map(song -> {
            Favorite f = new Favorite();
            f.setSongId(song.getId());
            f.setUserId(uid);
            return f;
        }).collect(Collectors.toList());
        favoriteService.batchDelete(favorites);
        return KeleResponseEntity.builder().ok().build();
    }
}
