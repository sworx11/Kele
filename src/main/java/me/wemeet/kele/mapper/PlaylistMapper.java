package me.wemeet.kele.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.wemeet.kele.entity.Playlist;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.wemeet.kele.entity.Song;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Quino Wu
 * @since 2021-12-04
 */
@Repository
public interface PlaylistMapper extends BaseMapper<Playlist> {
    IPage<Playlist> listByFavorite(Page<Playlist> page, long userId);
    List<Song> listSongsByPlaylist(long playlistId);
}
