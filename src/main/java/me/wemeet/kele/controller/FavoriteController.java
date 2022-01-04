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

import java.util.List;

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

    @DeleteMapping("")
    public KeleResponseEntity<?> undoFavorite(String userId, String songmid, String source) {
        Song song = songService.getBySongmidAndSource(songmid, source);
        favoriteService.deleteByUserAndSong(Long.parseLong(userId), song.getId());
        return KeleResponseEntity.builder().ok().build();
    }
}
