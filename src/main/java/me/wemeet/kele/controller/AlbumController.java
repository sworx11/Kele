package me.wemeet.kele.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.wemeet.kele.common.response.KeleResponseEntity;
import me.wemeet.kele.entity.Album;
import me.wemeet.kele.service.IAlbumService;
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
 * @since 2022-03-26
 */
@RestController
@RequestMapping("/album")
public class AlbumController {

    private IAlbumService albumService;

    @Autowired
    public void setAlbumService(IAlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("favorite")
    public KeleResponseEntity<IPage<Album>> listByFavorite(String userId, int page, int limit) {
        IPage<Album> result = albumService.listByFavorite(new Page<>(page, limit), Long.parseLong(userId));
        return KeleResponseEntity.<IPage<Album>>builder().ok(result).build();
    }

    @PostMapping("favorite")
    public KeleResponseEntity<?> doFavorite(String userId, @RequestBody Album album) {
        albumService.doFavorite(Long.parseLong(userId), album);
        return KeleResponseEntity.builder().ok().build();
    }

    @DeleteMapping("favorite")
    public KeleResponseEntity<?> unFavorite(String userId, String albumId) {
        albumService.unDoFavorite(Long.parseLong(userId), Long.parseLong(albumId));
        return KeleResponseEntity.builder().ok().build();
    }

    @DeleteMapping("favorite/batch")
    public KeleResponseEntity<?> batchUnFavorite(String userId, List<String> ids) {
        albumService.batchUnDoFavorite(Long.parseLong(userId), ids.parallelStream().map(Long::parseLong).collect(Collectors.toList()));
        return KeleResponseEntity.builder().ok().build();
    }

    @PostMapping("view")
    public KeleResponseEntity<Album> view(@RequestBody Album album) {
        album = albumService.view(album);
        return KeleResponseEntity.<Album>builder().ok(album).build();
    }

    @PostMapping("play")
    public KeleResponseEntity<?> play(@RequestBody Album album) {
        albumService.play(album);
        return KeleResponseEntity.builder().ok().build();
    }
}
