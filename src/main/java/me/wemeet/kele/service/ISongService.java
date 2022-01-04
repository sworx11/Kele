package me.wemeet.kele.service;

import me.wemeet.kele.entity.Song;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
