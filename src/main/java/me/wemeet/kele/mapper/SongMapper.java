package me.wemeet.kele.mapper;

import me.wemeet.kele.entity.Song;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Quino Wu
 * @since 2021-12-04
 */
@Repository
public interface SongMapper extends BaseMapper<Song> {
    Song getBySongmidAndSource(String songmid, String source);
}
