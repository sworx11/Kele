package me.wemeet.kele.service;

import me.wemeet.kele.entity.Song;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Quino Wu
 * @since 2021-12-04
 */
public interface ISongService extends IService<Song> {
    Song insertOrUpdate(Song song);
    Song getBySongmidAndSource(String songmid, String source);
    void batchInsertOrUpdate(List<Song> songs);
    List<Song> batchInsertOrUpdateReturnSong(List<Song> songs);
}
