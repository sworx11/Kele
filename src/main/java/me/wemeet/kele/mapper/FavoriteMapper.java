package me.wemeet.kele.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.wemeet.kele.entity.Favorite;
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
public interface FavoriteMapper extends BaseMapper<Favorite> {
    IPage<Song> listFavoriteByUser(Page<Song> page, long userId);
    int countByUserAndSong(long userId, long songId);
    void deleteByUserAndSong(long userId, long songId);
    List<Song> allFavoriteByUser(long userId);
}
