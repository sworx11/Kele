package me.wemeet.kele.controller;


import me.wemeet.kele.common.response.KeleResponseEntity;
import me.wemeet.kele.entity.Song;
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
@RequestMapping("/song")
public class SongController {
    private ISongService songService;

    @Autowired
    public void setSongService(ISongService songService) {
        this.songService = songService;
    }

    @PostMapping("")
    public KeleResponseEntity<?> saveSong(@RequestBody Song song) {
        songService.insertOrUpdate(song);
        return KeleResponseEntity.builder().ok().build();
    }

    @PostMapping("/batch")
    public KeleResponseEntity<?> batchSaveSong(@RequestBody List<Song> songs) {
        songService.batchInsertOrUpdate(songs);
        return KeleResponseEntity.builder().ok().build();
    }
}
